"use client"

import { useState } from "react"
import Link from "next/link"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Home, User, Mail, Lock, Eye, EyeOff, ArrowRight, Users, Building2, CheckCircle2 } from "lucide-react"
import { cn } from "@/lib/utils"

export default function RegisterPage() {
  const [showPassword, setShowPassword] = useState(false)
  const [userType, setUserType] = useState<"tenant" | "landlord">("tenant")

  return (
    <div className="min-h-screen bg-[#0f172a]">
      {/* Header */}
      <header className="border-b border-slate-800">
        <div className="container mx-auto flex h-16 items-center justify-between px-4">
          <Link href="/" className="flex items-center gap-2">
            <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-primary">
              <Home className="h-5 w-5 text-primary-foreground" />
            </div>
            <span className="text-lg font-bold text-slate-100">GharSaathi</span>
          </Link>
          <div className="flex items-center gap-4">
            <span className="text-sm text-slate-400">Already a member?</span>
            <Button
              variant="outline"
              asChild
              className="border-slate-700 bg-transparent text-slate-100 hover:bg-slate-800"
            >
              <Link href="/auth/login">Login</Link>
            </Button>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="container mx-auto flex min-h-[calc(100vh-64px)] items-center justify-center px-4 py-12">
        {/* Decorative Elements */}
        <div className="pointer-events-none absolute left-20 top-1/2 h-64 w-64 -translate-y-1/2 rounded-full bg-slate-800/50 blur-3xl" />
        <div className="pointer-events-none absolute right-20 top-1/3 h-48 w-48 rounded-2xl rotate-12 border-4 border-slate-700/50" />

        {/* Registration Card */}
        <div className="relative w-full max-w-md">
          <div className="rounded-3xl border border-slate-700 bg-slate-800/50 p-8 backdrop-blur">
            {/* Header */}
            <div className="text-center">
              <h1 className="text-2xl font-bold text-slate-100">Create an account</h1>
              <p className="mt-2 text-slate-400">Find your perfect home or verify tenants instantly.</p>
            </div>

            {/* User Type Toggle */}
            <div className="mt-8 flex rounded-xl bg-slate-700/50 p-1">
              <button
                type="button"
                onClick={() => setUserType("tenant")}
                className={cn(
                  "flex flex-1 items-center justify-center gap-2 rounded-lg py-3 text-sm font-medium transition-colors",
                  userType === "tenant" ? "bg-slate-600 text-slate-100" : "text-slate-400 hover:text-slate-300",
                )}
              >
                <Users className="h-4 w-4" />
                Tenant
              </button>
              <button
                type="button"
                onClick={() => setUserType("landlord")}
                className={cn(
                  "flex flex-1 items-center justify-center gap-2 rounded-lg py-3 text-sm font-medium transition-colors",
                  userType === "landlord" ? "bg-slate-600 text-slate-100" : "text-slate-400 hover:text-slate-300",
                )}
              >
                <Building2 className="h-4 w-4" />
                Landlord
              </button>
            </div>

            {/* Progress */}
            <div className="mt-6 flex items-center justify-between text-sm">
              <span className="text-slate-400">ACCOUNT DETAILS</span>
              <span className="text-slate-400">STEP 1 OF 3</span>
            </div>
            <div className="mt-2 h-1 rounded-full bg-slate-700">
              <div className="h-1 w-1/3 rounded-full bg-primary" />
            </div>

            {/* Form */}
            <form className="mt-6 space-y-4">
              <div>
                <Label htmlFor="name" className="text-slate-300">
                  Full Name
                </Label>
                <div className="relative mt-2">
                  <User className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-slate-500" />
                  <Input
                    id="name"
                    type="text"
                    placeholder="e.g. Aarav Sharma"
                    className="pl-10 h-12 bg-slate-700/50 border-slate-600 text-slate-100 placeholder:text-slate-500"
                  />
                </div>
              </div>

              <div>
                <Label htmlFor="email" className="text-slate-300">
                  Email Address
                </Label>
                <div className="relative mt-2">
                  <Mail className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-slate-500" />
                  <Input
                    id="email"
                    type="email"
                    placeholder="name@example.com"
                    className="pl-10 h-12 bg-slate-700/50 border-slate-600 text-slate-100 placeholder:text-slate-500"
                  />
                </div>
              </div>

              <div>
                <Label htmlFor="phone" className="text-slate-300">
                  Mobile Number
                </Label>
                <div className="relative mt-2 flex">
                  <div className="flex items-center gap-2 rounded-l-md border border-r-0 border-slate-600 bg-slate-700/50 px-3">
                    <span className="text-lg">🇳🇵</span>
                    <span className="text-slate-400">+977</span>
                  </div>
                  <Input
                    id="phone"
                    type="tel"
                    placeholder="98XXXXXXXX"
                    className="rounded-l-none h-12 bg-slate-700/50 border-slate-600 text-slate-100 placeholder:text-slate-500"
                  />
                </div>
              </div>

              <div>
                <Label htmlFor="password" className="text-slate-300">
                  Password
                </Label>
                <div className="relative mt-2">
                  <Lock className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-slate-500" />
                  <Input
                    id="password"
                    type={showPassword ? "text" : "password"}
                    placeholder="Create a strong password"
                    className="pl-10 pr-10 h-12 bg-slate-700/50 border-slate-600 text-slate-100 placeholder:text-slate-500"
                  />
                  <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-500 hover:text-slate-300"
                  >
                    {showPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                  </button>
                </div>
              </div>

              <Button type="submit" className="w-full h-12 text-base gap-2 bg-primary hover:bg-primary/90">
                Continue
                <ArrowRight className="h-4 w-4" />
              </Button>
            </form>

            {/* Divider */}
            <div className="relative my-6">
              <div className="absolute inset-0 flex items-center">
                <div className="w-full border-t border-slate-700" />
              </div>
              <div className="relative flex justify-center text-xs uppercase">
                <span className="bg-slate-800/50 px-2 text-slate-500">Or continue with</span>
              </div>
            </div>

            {/* Social Login */}
            <div className="grid grid-cols-2 gap-4">
              <Button
                variant="outline"
                className="h-12 border-slate-600 bg-transparent text-slate-100 hover:bg-slate-700"
              >
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
              <Button
                variant="outline"
                className="h-12 border-slate-600 bg-transparent text-slate-100 hover:bg-slate-700"
              >
                <svg className="mr-2 h-5 w-5 text-[#1877F2]" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z" />
                </svg>
                Facebook
              </Button>
            </div>

            {/* Trust Indicator */}
            <div className="mt-6 flex items-center justify-center gap-2 text-sm text-emerald-400">
              <CheckCircle2 className="h-4 w-4" />
              Trusted by 10,000+ users across Nepal
            </div>
          </div>

          {/* Footer */}
          <p className="mt-6 text-center text-sm text-slate-400">
            By joining, you agree to our{" "}
            <Link href="/terms" className="text-primary hover:underline">
              Terms of Service
            </Link>{" "}
            and{" "}
            <Link href="/privacy" className="text-primary hover:underline">
              Privacy Policy
            </Link>
          </p>
        </div>
      </main>
    </div>
  )
}
