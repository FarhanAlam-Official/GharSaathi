import { AxiosError } from 'axios'
import { HTTP_STATUS } from '@/lib/constants/api'

/**
 * API Error Response Structure
 */
export interface APIError {
  status: number
  message: string
  errors?: Record<string, string[]>
  timestamp?: string
}

/**
 * User-friendly error messages
 */
const ERROR_MESSAGES: Record<number, string> = {
  [HTTP_STATUS.BAD_REQUEST]: 'Invalid request. Please check your input.',
  [HTTP_STATUS.UNAUTHORIZED]: 'Please login to continue.',
  [HTTP_STATUS.FORBIDDEN]: 'You do not have permission to perform this action.',
  [HTTP_STATUS.NOT_FOUND]: 'The requested resource was not found.',
  [HTTP_STATUS.CONFLICT]: 'This resource already exists.',
  [HTTP_STATUS.INTERNAL_SERVER_ERROR]: 'Something went wrong on our end. Please try again later.',
  [HTTP_STATUS.SERVICE_UNAVAILABLE]: 'Service is temporarily unavailable.',
}

/**
 * Handle API errors and return user-friendly messages
 */
export const handleAPIError = (error: unknown): APIError => {
  // Handle Axios errors
  if (error instanceof AxiosError) {
    const status = error.response?.status || HTTP_STATUS.INTERNAL_SERVER_ERROR
    const responseData = error.response?.data

    // Extract error message from response
    let message = ERROR_MESSAGES[status] || 'An unexpected error occurred.'
    
    if (responseData) {
      // Backend may send error in different formats
      if (typeof responseData === 'string') {
        message = responseData
      } else if (responseData.message) {
        message = responseData.message
      } else if (responseData.error) {
        message = responseData.error
      }
    }

    // Extract validation errors if present
    const errors = responseData?.errors || responseData?.validationErrors

    return {
      status,
      message,
      errors,
      timestamp: new Date().toISOString(),
    }
  }

  // Handle network errors
  if (error instanceof Error) {
    return {
      status: 0,
      message: error.message || 'Network error. Please check your connection.',
      timestamp: new Date().toISOString(),
    }
  }

  // Handle unknown errors
  return {
    status: HTTP_STATUS.INTERNAL_SERVER_ERROR,
    message: 'An unexpected error occurred.',
    timestamp: new Date().toISOString(),
  }
}

/**
 * Get user-friendly error message for display
 */
export const getErrorMessage = (error: unknown): string => {
  const apiError = handleAPIError(error)
  return apiError.message
}

/**
 * Get validation errors from API response
 */
export const getValidationErrors = (error: unknown): Record<string, string[]> | undefined => {
  const apiError = handleAPIError(error)
  return apiError.errors
}

/**
 * Check if error is a specific HTTP status
 */
export const isErrorStatus = (error: unknown, status: number): boolean => {
  if (error instanceof AxiosError) {
    return error.response?.status === status
  }
  return false
}

/**
 * Check if error is authentication related
 */
export const isAuthError = (error: unknown): boolean => {
  return isErrorStatus(error, HTTP_STATUS.UNAUTHORIZED) || 
         isErrorStatus(error, HTTP_STATUS.FORBIDDEN)
}

/**
 * Check if error is validation related
 */
export const isValidationError = (error: unknown): boolean => {
  return isErrorStatus(error, HTTP_STATUS.BAD_REQUEST) || 
         isErrorStatus(error, HTTP_STATUS.CONFLICT)
}
