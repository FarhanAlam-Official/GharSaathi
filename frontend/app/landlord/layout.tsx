import type React from "react"
import { DashboardSidebar } from "@/components/dashboard-sidebar"
import { ProtectedRoute } from "@/components/ProtectedRoute"

export default function LandlordLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <ProtectedRoute allowedRoles={['LANDLORD']}>
      <div className="flex min-h-screen">
        <DashboardSidebar variant="landlord" />
        <main className="flex-1 bg-background">{children}</main>
      </div>
    </ProtectedRoute>
  )
}
