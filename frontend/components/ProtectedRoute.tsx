'use client'

import { useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { useAuth } from '@/contexts/AuthContext'
import type { Role } from '@/types/auth.types'
import { Loader2 } from 'lucide-react'

interface ProtectedRouteProps {
  children: React.ReactNode
  allowedRoles?: Role[]
  requireAuth?: boolean
}

/**
 * Client-side route protection component
 */
export function ProtectedRoute({
  children,
  allowedRoles,
  requireAuth = true,
}: ProtectedRouteProps) {
  const { user, isAuthenticated, isLoading } = useAuth()
  const router = useRouter()

  useEffect(() => {
    if (isLoading) return

    // Redirect to login if authentication is required but user is not authenticated
    if (requireAuth && !isAuthenticated) {
      router.push('/auth/login')
      return
    }

    // Check if user has required role
    if (isAuthenticated && allowedRoles && allowedRoles.length > 0) {
      if (!user || !allowedRoles.includes(user.role)) {
        // Redirect to unauthorized page
        router.push('/401')
        return
      }
    }
  }, [isLoading, isAuthenticated, user, allowedRoles, requireAuth, router])

  // Show loading state while checking authentication
  if (isLoading) {
    return (
      <div className="flex h-screen items-center justify-center">
        <div className="flex flex-col items-center gap-4">
          <Loader2 className="h-8 w-8 animate-spin text-primary" />
          <p className="text-sm text-muted-foreground">Loading...</p>
        </div>
      </div>
    )
  }

  // Don't render anything if redirecting
  if (requireAuth && !isAuthenticated) {
    return null
  }

  // Don't render if user doesn't have required role
  if (isAuthenticated && allowedRoles && allowedRoles.length > 0) {
    if (!user || !allowedRoles.includes(user.role)) {
      return null
    }
  }

  return <>{children}</>
}
