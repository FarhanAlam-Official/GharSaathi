"use client"

import { useState } from "react"
import { useForm } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod"
import * as z from "zod"
import Link from "next/link"
import Image from "next/image"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Alert, AlertDescription } from "@/components/ui/alert"
import { Home, Mail, Lock, Eye, EyeOff, ArrowRight, AlertCircle } from "lucide-react"
import { useAuth } from "@/contexts/AuthContext"

// Validation schema
const loginSchema = z.object({
  email: z.string().email('Invalid email address'),
  password: z.string().min(6, 'Password must be at least 6 characters'),
})

type LoginFormData = z.infer<typeof loginSchema>

export default function LoginPage() {
  const [showPassword, setShowPassword] = useState(false)
  const [error, setError] = useState<string>('')
  const { login, isLoading } = useAuth()

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
  })

  const onSubmit = async (data: LoginFormData) => {
    try {
      setError('')
      await login(data)
      // Redirect is handled by AuthContext
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Login failed. Please try again.')
    }
  }

  return (
    <div className="relative min-h-screen flex items-center justify-center p-4">
      {/* Background Image */}
      <div className="absolute inset-0 z-0">
        <Image src="/modern-apartment-interior-with-natural-light-woode.jpg" alt="Background" fill className="object-cover" priority />
        <div className="absolute inset-0 bg-background/10 backdrop-blur-[2px]" />
      </div>

      {/* Login Card */}
      <div className="relative z-10 w-full max-w-md">
        <div className="rounded-3xl bg-background/80 backdrop-blur-xl border border-border/50 p-8 shadow-2xl">
          {/* Logo */}
          <div className="flex justify-center">
            <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-primary">
              <Home className="h-7 w-7 text-primary-foreground" />
            </div>
          </div>

          {/* Header */}
          <div className="mt-6 text-center">
            <h1 className="text-2xl font-bold text-foreground">Welcome to GharSaathi</h1>
            <p className="mt-2 text-primary">Find your perfect space in Nepal</p>
          </div>

          {/* Form */}
          <form onSubmit={handleSubmit(onSubmit)} className="mt-8 space-y-5">
            {error && (
              <Alert variant="destructive">
                <AlertCircle className="h-4 w-4" />
                <AlertDescription>{error}</AlertDescription>
              </Alert>
            )}

            <div>
              <Label htmlFor="email" className="text-foreground">
                Email
              </Label>
              <div className="relative mt-2">
                <Mail className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                <Input
                  id="email"
                  type="email"
                  placeholder="Enter your email"
                  className="pl-10 h-12 bg-background"
                  {...register('email')}
                />
              </div>
              {errors.email && (
                <p className="mt-1 text-sm text-destructive">{errors.email.message}</p>
              )}
            </div>

            <div>
              <div className="flex items-center justify-between">
                <Label htmlFor="password" className="text-foreground">
                  Password
                </Label>
                <Link href="/auth/forgot-password" className="text-sm text-primary hover:underline">
                  Forgot Password?
                </Link>
              </div>
              <div className="relative mt-2">
                <Lock className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                <Input
                  id="password"
                  type={showPassword ? "text" : "password"}
                  placeholder="Enter your password"
                  className="pl-10 pr-10 h-12 bg-background"
                  {...register('password')}
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground"
                >
                  {showPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
              {errors.password && (
                <p className="mt-1 text-sm text-destructive">{errors.password.message}</p>
              )}
            </div>

            <Button type="submit" className="w-full h-12 text-base gap-2" disabled={isLoading}>
              {isLoading ? 'Logging in...' : 'Log In'}
              <ArrowRight className="h-4 w-4" />
            </Button>
          </form>

          {/* Divider */}
          <div className="relative my-8">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t border-border" />
            </div>
            <div className="relative flex justify-center text-xs uppercase">
              <span className="bg-background/80 px-2 text-muted-foreground">Or continue with</span>
            </div>
          </div>

          {/* Social Login */}
          <div className="grid grid-cols-2 gap-4">
            <Button variant="outline" className="h-12 bg-background">
              <svg className="mr-2 h-5 w-5" viewBox="0 0 24 24">
                <path
                  d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"
                  fill="#4285F4"
                />
                <path
                  d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"
                  fill="#34A853"
                />
                <path
                  d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"
                  fill="#FBBC05"
                />
                <path
                  d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"
                  fill="#EA4335"
                />
              </svg>
              Google
            </Button>
            <Button variant="outline" className="h-12 bg-background">
              <svg className="mr-2 h-5 w-5 text-[#1877F2]" fill="currentColor" viewBox="0 0 24 24">
                <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z" />
              </svg>
              Facebook
            </Button>
          </div>

          {/* Sign Up Link */}
          <p className="mt-8 text-center text-sm text-muted-foreground">
            Don&apos;t have an account?{" "}
            <Link href="/auth/register" className="font-semibold text-primary hover:underline">
              Sign up
            </Link>
          </p>
        </div>

        {/* Footer Links */}
        <div className="mt-6 flex justify-center gap-6 text-sm text-muted-foreground">
          <Link href="/terms" className="hover:text-foreground">
            Terms of Service
          </Link>
          <Link href="/privacy" className="hover:text-foreground">
            Privacy Policy
          </Link>
          <Link href="/help" className="hover:text-foreground">
            Help Center
          </Link>
        </div>
      </div>
    </div>
  )
}
