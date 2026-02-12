import axios, { AxiosInstance, AxiosError, AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig } from 'axios'
import { API_CONFIG, HTTP_STATUS } from '@/lib/constants/api'

/**
 * Create Axios instance with default configuration
 */
export const apiClient: AxiosInstance = axios.create({
  baseURL: API_CONFIG.baseURL,
  timeout: API_CONFIG.timeout,
  headers: API_CONFIG.headers,
})

/**
 * Request interceptor - Add JWT token to headers
 */
apiClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // Get token from localStorage
    const token = getAccessToken()
    
    // Add token to headers if available
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }

    // Log request in development
    if (process.env.NODE_ENV === 'development') {
      console.log('🚀 API Request:', {
        method: config.method?.toUpperCase(),
        url: config.url,
        data: config.data,
      })
    }

    return config
  },
  (error: AxiosError) => {
    console.error('❌ Request Error:', error)
    return Promise.reject(error)
  }
)

/**
 * Response interceptor - Handle errors and token refresh
 */
apiClient.interceptors.response.use(
  (response: AxiosResponse) => {
    // Log response in development
    if (process.env.NODE_ENV === 'development') {
      console.log('✅ API Response:', {
        status: response.status,
        url: response.config.url,
        data: response.data,
      })
    }

    return response
  },
  async (error: AxiosError) => {
    const originalRequest = error.config as InternalAxiosRequestConfig & { _retry?: boolean }

    // Log error in development with full details
    if (process.env.NODE_ENV === 'development') {
      console.error('❌ API Error:', {
        status: error.response?.status,
        url: error.config?.url,
        message: error.message,
        data: error.response?.data,
        fullError: error,
      })
    }

    // Handle 401 Unauthorized - Try to refresh token
    if (error.response?.status === HTTP_STATUS.UNAUTHORIZED && !originalRequest._retry) {
      originalRequest._retry = true

      try {
        const refreshToken = getRefreshToken()
        
        if (refreshToken) {
          // Attempt to refresh the access token
          const response = await axios.post(`${API_CONFIG.baseURL}/auth/refresh`, {
            refreshToken,
          })

          const { accessToken } = response.data

          // Save new access token
          setAccessToken(accessToken)

          // Retry the original request with new token
          if (originalRequest.headers) {
            originalRequest.headers.Authorization = `Bearer ${accessToken}`
          }
          
          return apiClient(originalRequest)
        }
      } catch (refreshError) {
        // Refresh failed - clear tokens and redirect to login
        handleAuthenticationFailure()
        return Promise.reject(refreshError)
      }
    }

    // Handle 403 Forbidden - User doesn't have permission
    if (error.response?.status === HTTP_STATUS.FORBIDDEN) {
      console.error('🚫 Access Forbidden:', error.response.data)
      // Optionally redirect to unauthorized page
      if (typeof window !== 'undefined') {
        window.location.href = '/401'
      }
    }

    return Promise.reject(error)
  }
)

/**
 * Token management utilities
 */
const TOKEN_KEY = process.env.NEXT_PUBLIC_TOKEN_KEY || 'gharsaathi_token'
const REFRESH_TOKEN_KEY = process.env.NEXT_PUBLIC_REFRESH_TOKEN_KEY || 'gharsaathi_refresh_token'

export const getAccessToken = (): string | null => {
  if (typeof window === 'undefined') return null
  return localStorage.getItem(TOKEN_KEY)
}

export const setAccessToken = (token: string): void => {
  if (typeof window === 'undefined') return
  localStorage.setItem(TOKEN_KEY, token)
}

export const getRefreshToken = (): string | null => {
  if (typeof window === 'undefined') return null
  return localStorage.getItem(REFRESH_TOKEN_KEY)
}

export const setRefreshToken = (token: string): void => {
  if (typeof window === 'undefined') return
  localStorage.setItem(REFRESH_TOKEN_KEY, token)
}

export const clearTokens = (): void => {
  if (typeof window === 'undefined') return
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(REFRESH_TOKEN_KEY)
}

/**
 * Handle authentication failure
 */
const handleAuthenticationFailure = (): void => {
  clearTokens()
  
  // Redirect to login page
  if (typeof window !== 'undefined') {
    window.location.href = '/auth/login'
  }
}

/**
 * Export configured client
 */
export default apiClient
