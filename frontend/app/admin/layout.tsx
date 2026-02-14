import type React from "react"
import { DashboardSidebar } from "@/components/dashboard-sidebar"
import { ProtectedRoute } from "@/components/ProtectedRoute"

export default function AdminLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <ProtectedRoute allowedRoles={['ADMIN']}>
      <div className="flex min-h-screen">
        <DashboardSidebar variant="admin" />
        <main className="flex-1 bg-card">{children}</main>
      </div>
    </ProtectedRoute>
  )
}
