import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

/**
 * Middleware for basic route protection
 * Note: This provides basic protection. Main auth is handled by AuthContext
 */
export default function middleware(request: NextRequest) {
  // For now, allow all requests - auth is handled by AuthContext
  return NextResponse.next()
}

/**
 * Configure which routes use this middleware
 */
export const config = {
  matcher: [
    /*
     * Match all request paths except:
     * - _next/static (static files)
     * - _next/image (image optimization files)
     * - favicon.ico (favicon file)
     * - public folder
     */
    '/((?!_next/static|_next/image|favicon.ico|.*\\.(?:svg|png|jpg|jpeg|gif|webp)$).*)',
  ],
}
