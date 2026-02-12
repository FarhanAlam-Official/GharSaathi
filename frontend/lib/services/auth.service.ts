import apiClient, { setAccessToken, setRefreshToken, clearTokens } from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import type {
  LoginRequest,
  RegisterRequest,
  AuthResponse,
  TokenRefreshRequest,
  TokenRefreshResponse,
  User,
} from '@/types/auth.types'

/**
 * Authentication Service
 * Handles all authentication-related API calls
 */
class AuthService {
  /**
   * Register a new user
   */
  async register(data: RegisterRequest): Promise<AuthResponse> {
    // Transform data to match backend expectations
    const backendPayload = {
      email: data.email,
      password: data.password,
      fullName: `${data.firstName} ${data.lastName}`,
      role: data.role,
      phoneNumber: data.phoneNumber,
    }

    const response = await apiClient.post<AuthResponse>(
      API_ENDPOINTS.AUTH.REGISTER,
      backendPayload
    )
    
    // Store tokens
    setAccessToken(response.data.accessToken)
    setRefreshToken(response.data.refreshToken)
    
    return response.data
  }

  /**
   * Login user
   */
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await apiClient.post<AuthResponse>(
      API_ENDPOINTS.AUTH.LOGIN,
      credentials
    )
    
    // Store tokens
    setAccessToken(response.data.accessToken)
    setRefreshToken(response.data.refreshToken)
    
    return response.data
  }

  /**
   * Logout user (single device)
   */
  async logout(): Promise<void> {
    try {
      await apiClient.post(API_ENDPOINTS.AUTH.LOGOUT)
    } finally {
      // Always clear tokens even if API call fails
      clearTokens()
    }
  }

  /**
   * Logout from all devices
   */
  async logoutAll(): Promise<void> {
    try {
      await apiClient.post(API_ENDPOINTS.AUTH.LOGOUT_ALL)
    } finally {
      // Always clear tokens even if API call fails
      clearTokens()
    }
  }

  /**
   * Refresh access token
   */
  async refreshToken(refreshToken: string): Promise<TokenRefreshResponse> {
    const response = await apiClient.post<TokenRefreshResponse>(
      API_ENDPOINTS.AUTH.REFRESH,
      { refreshToken } as TokenRefreshRequest
    )
    
    // Update tokens
    setAccessToken(response.data.accessToken)
    if (response.data.refreshToken) {
      setRefreshToken(response.data.refreshToken)
    }
    
    return response.data
  }

  /**
   * Get current user profile
   */
  async getCurrentUser(): Promise<User> {
    const response = await apiClient.get<User>(API_ENDPOINTS.PROFILE.GET)
    return response.data
  }
}

// Export singleton instance
export const authService = new AuthService()
export default authService
