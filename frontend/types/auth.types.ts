/**
 * User roles in the system
 */
export type Role = 'TENANT' | 'LANDLORD' | 'ADMIN'

/**
 * User entity
 */
export interface User {
  id: number
  email: string
  firstName: string
  lastName: string
  phoneNumber?: string
  role: Role
  isActive: boolean
  isVerified: boolean
  profileImageUrl?: string
  createdAt: string
  updatedAt: string
}

/**
 * Registration request payload
 */
export interface RegisterRequest {
  email: string
  password: string
  firstName: string
  lastName: string
  role: Role
  phoneNumber?: string
}

/**
 * Login request payload
 */
export interface LoginRequest {
  email: string
  password: string
}

/**
 * Authentication response from backend
 */
export interface AuthResponse {
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
  userId: number
  email: string
  fullName: string
  role: Role
  message?: string
}

/**
 * Token refresh request
 */
export interface TokenRefreshRequest {
  refreshToken: string
}

/**
 * Token refresh response
 */
export interface TokenRefreshResponse {
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
}

/**
 * Authentication context state
 */
export interface AuthContextType {
  user: User | null
  isAuthenticated: boolean
  isLoading: boolean
  login: (credentials: LoginRequest) => Promise<void>
  register: (data: RegisterRequest) => Promise<void>
  logout: () => Promise<void>
  logoutAll: () => Promise<void>
  refreshToken: () => Promise<void>
}
