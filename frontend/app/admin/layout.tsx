import type React from "react"
import { DashboardSidebar } from "@/components/dashboard-sidebar"

export default function AdminLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="flex min-h-screen">
      <DashboardSidebar variant="admin" />
      <main className="flex-1 bg-card">{children}</main>
    </div>
  )
}
