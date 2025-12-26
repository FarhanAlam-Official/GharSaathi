"use client"

import type React from "react"

import Link from "next/link"
import { usePathname } from "next/navigation"
import { cn } from "@/lib/utils"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Button } from "@/components/ui/button"
import {
  Home,
  LayoutDashboard,
  Building2,
  Users,
  MessageSquare,
  FileText,
  CreditCard,
  Settings,
  LogOut,
  Heart,
  BarChart3,
  Shield,
  Plus,
  HelpCircle,
} from "lucide-react"

interface SidebarItem {
  label: string
  href: string
  icon: React.ElementType
  badge?: number
}

interface DashboardSidebarProps {
  variant: "tenant" | "landlord" | "admin"
}

const tenantItems: SidebarItem[] = [
  { label: "Dashboard", href: "/tenant/dashboard", icon: LayoutDashboard },
  { label: "Saved Homes", href: "/tenant/saved", icon: Heart },
  { label: "Applications", href: "/tenant/applications", icon: FileText },
  { label: "Messages", href: "/tenant/messages", icon: MessageSquare, badge: 2 },
  { label: "Lease Info", href: "/tenant/lease", icon: FileText },
  { label: "Settings", href: "/tenant/settings", icon: Settings },
]

const landlordItems: SidebarItem[] = [
  { label: "Dashboard", href: "/landlord/dashboard", icon: LayoutDashboard },
  { label: "Properties", href: "/landlord/properties", icon: Building2 },
  { label: "Tenants", href: "/landlord/tenants", icon: Users },
  { label: "Requests & Visits", href: "/landlord/requests", icon: MessageSquare, badge: 3 },
  { label: "Financials", href: "/landlord/financials", icon: CreditCard },
  { label: "Analytics", href: "/landlord/analytics", icon: BarChart3 },
  { label: "Settings", href: "/landlord/settings", icon: Settings },
]

const adminItems: SidebarItem[] = [
  { label: "Dashboard", href: "/admin/dashboard", icon: LayoutDashboard },
  { label: "User Management", href: "/admin/users", icon: Users },
  { label: "Property Listings", href: "/admin/properties", icon: Building2 },
  { label: "Financials", href: "/admin/financials", icon: CreditCard },
  { label: "Reports", href: "/admin/reports", icon: BarChart3 },
  { label: "Settings", href: "/admin/settings", icon: Settings },
  { label: "Roles & Permissions", href: "/admin/roles", icon: Shield },
]

export function DashboardSidebar({ variant }: DashboardSidebarProps) {
  const pathname = usePathname()

  const items = variant === "tenant" ? tenantItems : variant === "landlord" ? landlordItems : adminItems
  const isDark = variant === "landlord" || variant === "admin"

  const user = {
    name: variant === "admin" ? "Admin User" : variant === "landlord" ? "Ramesh K." : "Aarav Sharma",
    role: variant === "admin" ? "Super Admin" : variant === "landlord" ? "Landlord" : "Tenant",
    avatar: "/placeholder.svg?height=40&width=40",
  }

  return (
    <aside
      className={cn(
        "flex h-screen w-64 flex-col border-r",
        isDark ? "bg-[#0f172a] border-[#1e293b] text-slate-100" : "bg-card border-border text-foreground",
      )}
    >
      {/* Logo */}
      <div
        className="flex h-16 items-center gap-2 border-b px-6"
        style={{ borderColor: isDark ? "#1e293b" : undefined }}
      >
        <div
          className={cn("flex h-9 w-9 items-center justify-center rounded-lg", isDark ? "bg-primary" : "bg-primary")}
        >
          <Home className="h-5 w-5 text-primary-foreground" />
        </div>
        <div className="flex flex-col">
          <span className="font-bold">GharSaathi</span>
          <span className={cn("text-xs", isDark ? "text-primary" : "text-primary")}>
            {variant === "admin" ? "Admin Panel" : `${variant.charAt(0).toUpperCase() + variant.slice(1)} Portal`}
          </span>
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 space-y-1 overflow-y-auto p-4">
        {variant !== "admin" && (
          <p
            className={cn(
              "mb-2 px-3 text-xs font-medium uppercase tracking-wider",
              isDark ? "text-slate-500" : "text-muted-foreground",
            )}
          >
            Main Menu
          </p>
        )}
        {variant === "admin" && (
          <p className={cn("mb-2 px-3 text-xs font-medium uppercase tracking-wider", "text-slate-500")}>Main Menu</p>
        )}
        {items.slice(0, variant === "admin" ? 5 : 6).map((item) => (
          <Link
            key={item.href}
            href={item.href}
            className={cn(
              "flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-colors",
              pathname === item.href
                ? isDark
                  ? "bg-primary/20 text-primary"
                  : "bg-primary/10 text-primary"
                : isDark
                  ? "text-slate-400 hover:bg-slate-800 hover:text-slate-100"
                  : "text-muted-foreground hover:bg-accent hover:text-foreground",
            )}
          >
            <item.icon className="h-5 w-5" />
            {item.label}
            {item.badge && (
              <span className="ml-auto flex h-5 w-5 items-center justify-center rounded-full bg-primary text-[10px] font-medium text-primary-foreground">
                {item.badge}
              </span>
            )}
          </Link>
        ))}

        {variant === "admin" && (
          <>
            <p className={cn("mb-2 mt-6 px-3 text-xs font-medium uppercase tracking-wider", "text-slate-500")}>
              System
            </p>
            {items.slice(5).map((item) => (
              <Link
                key={item.href}
                href={item.href}
                className={cn(
                  "flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-colors",
                  pathname === item.href
                    ? "bg-primary/20 text-primary"
                    : "text-slate-400 hover:bg-slate-800 hover:text-slate-100",
                )}
              >
                <item.icon className="h-5 w-5" />
                {item.label}
              </Link>
            ))}
          </>
        )}

        {/* Occupancy indicator for landlord */}
        {variant === "landlord" && (
          <div className="mt-6 px-3">
            <div className="flex items-center justify-between text-sm">
              <span className="text-slate-400">Occupancy</span>
              <span className="text-emerald-400 font-medium">92%</span>
            </div>
            <div className="mt-2 h-2 rounded-full bg-slate-700">
              <div className="h-2 rounded-full bg-emerald-500" style={{ width: "92%" }} />
            </div>
          </div>
        )}
      </nav>

      {/* Help Card for tenant */}
      {variant === "tenant" && (
        <div className="mx-4 mb-4 rounded-xl bg-primary p-4 text-primary-foreground">
          <div className="flex items-center gap-3">
            <div className="flex h-10 w-10 items-center justify-center rounded-full bg-primary-foreground/20">
              <HelpCircle className="h-5 w-5" />
            </div>
            <div>
              <p className="font-semibold">Need Help?</p>
              <p className="text-xs opacity-80">Contact our support team for any queries.</p>
            </div>
          </div>
        </div>
      )}

      {/* Add Property Button for landlord */}
      {variant === "landlord" && (
        <div className="p-4">
          <Button className="w-full bg-primary hover:bg-primary/90">
            <Plus className="mr-2 h-4 w-4" />
            List New Property
          </Button>
        </div>
      )}

      {/* User Profile */}
      <div className={cn("flex items-center gap-3 border-t p-4", isDark ? "border-[#1e293b]" : "border-border")}>
        <Avatar className="h-10 w-10">
          <AvatarImage src={user.avatar || "/placeholder.svg"} />
          <AvatarFallback>{user.name.charAt(0)}</AvatarFallback>
        </Avatar>
        <div className="flex-1 min-w-0">
          <p className="text-sm font-medium truncate">{user.name}</p>
          <p className={cn("text-xs truncate", isDark ? "text-slate-400" : "text-muted-foreground")}>{user.role}</p>
        </div>
        <Button
          variant="ghost"
          size="icon"
          className={cn("shrink-0", isDark ? "text-slate-400 hover:text-slate-100" : "")}
        >
          <LogOut className="h-4 w-4" />
          <span className="sr-only">Log out</span>
        </Button>
      </div>
    </aside>
  )
}
