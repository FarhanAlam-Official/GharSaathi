import type React from "react"
import { DashboardSidebar } from "@/components/dashboard-sidebar"
import { ProtectedRoute } from "@/components/ProtectedRoute"

export default function TenantLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <ProtectedRoute allowedRoles={['TENANT']}>
      <div className="flex min-h-screen">
        <DashboardSidebar variant="tenant" />
        <main className="flex-1 bg-background">{children}</main>
      </div>
    </ProtectedRoute>
  )
}
