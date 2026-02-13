'use client'

import React, { createContext, useContext, useState, useEffect, useCallback } from 'react'
import { useRouter } from 'next/navigation'
import { authService } from '@/lib/services/auth.service'
import { getAccessToken, clearTokens } from '@/lib/api/client'
import { handleAPIError } from '@/lib/api/errorHandler'
import { DEFAULT_ROLE_REDIRECT } from '@/lib/constants/routes'
import type {
  AuthContextType,
  User,
  LoginRequest,
  RegisterRequest,
} from '@/types/auth.types'

/**
 * Create Auth Context
 */
const AuthContext = createContext<AuthContextType | undefined>(undefined)

/**
 * Auth Provider Props
 */
interface AuthProviderProps {
  children: React.ReactNode
}

/**
 * Auth Provider Component
 */
export function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<User | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const router = useRouter()

  /**
   * Check if user is authenticated on mount
   */
  useEffect(() => {
    const initAuth = async () => {
      const token = getAccessToken()
      
      if (!token) {
        setUser(null)
      }
      // Note: User data is already set during login/register
      // No need to fetch user on mount - just verify token exists
      
      setIsLoading(false)
    }

    initAuth()
  }, [])

  /**
   * Login user
   */
  const login = useCallback(async (credentials: LoginRequest) => {
    try {
      setIsLoading(true)
      const response = await authService.login(credentials)
      
      // Create user object from response
      const userData: User = {
        id: response.userId,
        email: response.email,
        firstName: response.fullName.split(' ')[0],
        lastName: response.fullName.split(' ').slice(1).join(' '),
        role: response.role,
        isActive: true,
        isVerified: true,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
      }
      
      setUser(userData)
      
      // Redirect to appropriate dashboard based on role
      const redirectPath = DEFAULT_ROLE_REDIRECT[response.role]
      router.push(redirectPath)
    } catch (error) {
      const apiError = handleAPIError(error)
      console.error('Login failed:', apiError)
      throw new Error(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }, [router])

  /**
   * Register new user
   */
  const register = useCallback(async (data: RegisterRequest) => {
    try {
      setIsLoading(true)
      const response = await authService.register(data)
      
      // Create user object from response
      const userData: User = {
        id: response.userId,
        email: response.email,
        firstName: response.fullName.split(' ')[0],
        lastName: response.fullName.split(' ').slice(1).join(' '),
        role: response.role,
        isActive: true,
        isVerified: true,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
      }
      
      setUser(userData)
      
      // Redirect to appropriate dashboard based on role
      const redirectPath = DEFAULT_ROLE_REDIRECT[response.role]
      router.push(redirectPath)
    } catch (error) {
      const apiError = handleAPIError(error)
      console.error('Registration failed:', apiError)
      throw new Error(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }, [router])

  /**
   * Logout user (single device)
   */
  const logout = useCallback(async () => {
    try {
      await authService.logout()
    } catch (error) {
      console.error('Logout error:', error)
    } finally {
      setUser(null)
      clearTokens()
      router.push('/auth/login')
    }
  }, [router])

  /**
   * Logout from all devices
   */
  const logoutAll = useCallback(async () => {
    try {
      await authService.logoutAll()
    } catch (error) {
      console.error('Logout all error:', error)
    } finally {
      setUser(null)
      clearTokens()
      router.push('/auth/login')
    }
  }, [router])

  /**
   * Refresh access token
   */
  const refreshToken = useCallback(async () => {
    try {
      const currentRefreshToken = localStorage.getItem('gharsaathi_refresh_token')
      if (currentRefreshToken) {
        await authService.refreshToken(currentRefreshToken)
      }
    } catch (error) {
      console.error('Token refresh error:', error)
      setUser(null)
      clearTokens()
      router.push('/auth/login')
    }
  }, [router])

  const value: AuthContextType = {
    user,
    isAuthenticated: !!user,
    isLoading,
    login,
    register,
    logout,
    logoutAll,
    refreshToken,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

/**
 * Custom hook to use auth context
 */
export function useAuth() {
  const context = useContext(AuthContext)
  
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  
  return context
}

/**
 * Export context for advanced usage
 */
export default AuthContext
