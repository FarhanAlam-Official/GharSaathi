"use client"

import { Card, CardContent } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Checkbox } from "@/components/ui/checkbox"
import {
  Search,
  Bell,
  HelpCircle,
  Download,
  Plus,
  Users,
  UserPlus,
  ShieldCheck,
  Building2,
  SlidersHorizontal,
  ChevronLeft,
  ChevronRight,
  MoreVertical,
} from "lucide-react"
import { mockUsers } from "@/lib/mock-data"
import Link from "next/link"

export default function AdminUsersPage() {
  return (
    <div className="flex-1 p-8">
      {/* Top Bar */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-2 text-sm text-muted-foreground">
          <Link href="/admin/dashboard" className="hover:text-foreground">
            Dashboard
          </Link>
          <span>/</span>
          <span className="text-foreground">User Management</span>
        </div>
        <div className="flex items-center gap-3">
          <Button variant="ghost" size="icon" className="relative">
            <Bell className="h-5 w-5" />
            <span className="absolute -top-1 -right-1 h-4 w-4 rounded-full bg-destructive text-[10px] font-medium text-destructive-foreground flex items-center justify-center">
              5
            </span>
          </Button>
          <Button variant="ghost" size="icon">
            <HelpCircle className="h-5 w-5" />
          </Button>
        </div>
      </div>

      {/* Header */}
      <div className="mt-8 flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-foreground">User Management</h1>
          <p className="mt-1 text-muted-foreground">Manage tenants, landlords, and oversee account verifications.</p>
        </div>
        <div className="flex items-center gap-3">
          <Button variant="outline" className="gap-2 bg-transparent">
            <Download className="h-4 w-4" />
            Export CSV
          </Button>
          <Button className="gap-2">
            <Plus className="h-4 w-4" />
            Add New User
          </Button>
        </div>
      </div>

      {/* Stats */}
      <div className="mt-8 grid gap-6 md:grid-cols-4">
        <Card>
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-muted-foreground">Total Users</p>
              <p className="mt-2 text-3xl font-bold">12,450</p>
            </div>
            <div className="flex flex-col items-end gap-2">
              <span className="text-sm text-emerald-500">+12%</span>
              <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-primary/10">
                <Users className="h-5 w-5 text-primary" />
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-muted-foreground">New This Week</p>
              <p className="mt-2 text-3xl font-bold">120</p>
            </div>
            <div className="flex flex-col items-end gap-2">
              <span className="text-sm text-emerald-500">+5%</span>
              <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-blue-100">
                <UserPlus className="h-5 w-5 text-blue-600" />
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-muted-foreground">Pending Verification</p>
              <p className="mt-2 text-3xl font-bold">45</p>
            </div>
            <div className="flex flex-col items-end gap-2">
              <span className="text-sm text-red-500">-2%</span>
              <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-amber-100">
                <ShieldCheck className="h-5 w-5 text-amber-600" />
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-muted-foreground">Active Landlords</p>
              <p className="mt-2 text-3xl font-bold">892</p>
            </div>
            <div className="flex flex-col items-end gap-2">
              <span className="text-sm text-emerald-500">+8%</span>
              <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-purple-100">
                <Building2 className="h-5 w-5 text-purple-600" />
              </div>
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Filters */}
      <Card className="mt-8">
        <CardContent className="p-6">
          <div className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
            <div className="relative flex-1 max-w-md">
              <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
              <Input type="search" placeholder="Search by name, email, or ID..." className="pl-10 bg-background" />
            </div>
            <div className="flex items-center gap-3">
              <Select defaultValue="all">
                <SelectTrigger className="w-[130px]">
                  <SelectValue placeholder="All Roles" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="all">All Roles</SelectItem>
                  <SelectItem value="tenant">Tenant</SelectItem>
                  <SelectItem value="landlord">Landlord</SelectItem>
                  <SelectItem value="admin">Admin</SelectItem>
                </SelectContent>
              </Select>
              <Select defaultValue="all">
                <SelectTrigger className="w-[130px]">
                  <SelectValue placeholder="All Status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="all">All Status</SelectItem>
                  <SelectItem value="active">Active</SelectItem>
                  <SelectItem value="pending">Pending</SelectItem>
                  <SelectItem value="verified">Verified</SelectItem>
                  <SelectItem value="suspended">Suspended</SelectItem>
                </SelectContent>
              </Select>
              <Button variant="outline" className="gap-2 bg-transparent">
                <SlidersHorizontal className="h-4 w-4" />
                More Filters
              </Button>
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Users Table */}
      <Card className="mt-6">
        <CardContent className="p-0">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-border">
                  <th className="p-4 text-left">
                    <Checkbox />
                  </th>
                  <th className="p-4 text-left text-xs font-medium uppercase text-muted-foreground">User</th>
                  <th className="p-4 text-left text-xs font-medium uppercase text-muted-foreground">Role</th>
                  <th className="p-4 text-left text-xs font-medium uppercase text-muted-foreground">Status</th>
                  <th className="p-4 text-left text-xs font-medium uppercase text-muted-foreground">
                    Properties / Activity
                  </th>
                  <th className="p-4 text-left text-xs font-medium uppercase text-muted-foreground">Joined Date</th>
                  <th className="p-4 text-left text-xs font-medium uppercase text-muted-foreground">Actions</th>
                </tr>
              </thead>
              <tbody>
                {mockUsers.map((user) => (
                  <tr key={user.id} className="border-b border-border hover:bg-muted/50">
                    <td className="p-4">
                      <Checkbox />
                    </td>
                    <td className="p-4">
                      <div className="flex items-center gap-3">
                        <Avatar className="h-10 w-10">
                          <AvatarImage src={user.avatar || "/placeholder.svg"} />
                          <AvatarFallback className="bg-primary/10 text-primary">
                            {user.name
                              .split(" ")
                              .map((n) => n[0])
                              .join("")}
                          </AvatarFallback>
                        </Avatar>
                        <div>
                          <p className="font-medium">{user.name}</p>
                          <p className="text-sm text-muted-foreground">{user.email}</p>
                        </div>
                      </div>
                    </td>
                    <td className="p-4">
                      <Badge
                        variant="outline"
                        className={
                          user.role === "landlord"
                            ? "border-primary text-primary"
                            : user.role === "tenant"
                              ? "border-emerald-500 text-emerald-600"
                              : "border-purple-500 text-purple-600"
                        }
                      >
                        {user.role.charAt(0).toUpperCase() + user.role.slice(1)}
                      </Badge>
                    </td>
                    <td className="p-4">
                      <Badge
                        className={
                          user.status === "verified"
                            ? "bg-emerald-100 text-emerald-700"
                            : user.status === "active"
                              ? "bg-emerald-100 text-emerald-700"
                              : user.status === "pending"
                                ? "bg-amber-100 text-amber-700"
                                : "bg-red-100 text-red-700"
                        }
                      >
                        ● {user.status.charAt(0).toUpperCase() + user.status.slice(1)}
                      </Badge>
                    </td>
                    <td className="p-4 text-sm text-muted-foreground">{user.activity || "-"}</td>
                    <td className="p-4 text-sm">{user.joinedDate}</td>
                    <td className="p-4">
                      <Button variant="ghost" size="icon">
                        <MoreVertical className="h-4 w-4" />
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          {/* Pagination */}
          <div className="flex items-center justify-between border-t border-border p-4">
            <p className="text-sm text-muted-foreground">
              Showing <span className="font-medium text-foreground">1</span> to{" "}
              <span className="font-medium text-foreground">5</span> of{" "}
              <span className="font-medium text-primary">12,450</span> results
            </p>
            <div className="flex items-center gap-2">
              <Button variant="outline" size="icon" className="h-9 w-9 bg-transparent">
                <ChevronLeft className="h-4 w-4" />
              </Button>
              {[1, 2, 3].map((page) => (
                <Button
                  key={page}
                  variant={page === 1 ? "default" : "outline"}
                  size="icon"
                  className={`h-9 w-9 ${page !== 1 ? "bg-transparent" : ""}`}
                >
                  {page}
                </Button>
              ))}
              <span className="px-2 text-muted-foreground">...</span>
              <Button variant="outline" size="icon" className="h-9 w-9 bg-transparent">
                42
              </Button>
              <Button variant="outline" size="icon" className="h-9 w-9 bg-transparent">
                <ChevronRight className="h-4 w-4" />
              </Button>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  )
}
