"use client"

import { StatsCard } from "@/components/stats-card"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Badge } from "@/components/ui/badge"
import {
  Search,
  Bell,
  MessageSquare,
  Plus,
  Wallet,
  Building2,
  Users,
  TrendingUp,
  ArrowRight,
  Phone,
} from "lucide-react"
import { mockTenantRequests } from "@/lib/mock-data"
import Image from "next/image"

export default function LandlordDashboardPage() {
  return (
    <div className="flex-1 p-8">
      {/* Top Bar */}
      <div className="flex items-center justify-between">
        <div className="relative flex-1 max-w-md">
          <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
          <Input type="search" placeholder="Search properties, tenants..." className="pl-10 bg-card" />
        </div>
        <div className="flex items-center gap-4">
          <Button variant="ghost" size="icon" className="relative">
            <Bell className="h-5 w-5" />
            <span className="absolute -top-1 -right-1 h-4 w-4 rounded-full bg-destructive text-[10px] font-medium text-destructive-foreground flex items-center justify-center">
              3
            </span>
          </Button>
          <Button variant="ghost" size="icon">
            <MessageSquare className="h-5 w-5" />
          </Button>
        </div>
      </div>

      {/* Welcome Section */}
      <div className="mt-8 flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-foreground">Welcome back, Ramesh!</h1>
          <p className="mt-1 text-muted-foreground">
            Your portfolio performance overview for <span className="text-primary font-medium">October 2023</span>.
          </p>
        </div>
        <Button className="gap-2">
          <Plus className="h-4 w-4" />
          Add New Property
        </Button>
      </div>

      {/* Stats Cards */}
      <div className="mt-8 grid gap-6 md:grid-cols-3">
        <StatsCard
          title="Total Revenue"
          value="Rs. 1,50,000"
          change={{ value: "+12%", trend: "up" }}
          icon={<Wallet className="h-6 w-6 text-primary" />}
        />
        <StatsCard
          title="Occupancy Rate"
          value="92%"
          change={{ value: "-2%", trend: "down" }}
          subtitle="2 Units Vacant"
          icon={<Building2 className="h-6 w-6 text-emerald-500" />}
        />
        <StatsCard
          title="Active Leads"
          value="14"
          change={{ value: "+3 New inquiries today", trend: "neutral" }}
          icon={<Users className="h-6 w-6 text-primary" />}
        />
      </div>

      {/* Charts and Recent Inquiries */}
      <div className="mt-8 grid gap-6 lg:grid-cols-3">
        {/* Income Trends Chart */}
        <Card className="lg:col-span-2">
          <CardHeader className="flex flex-row items-center justify-between">
            <div>
              <CardTitle>Income Trends</CardTitle>
              <p className="text-sm text-muted-foreground">Last 6 Months Performance</p>
            </div>
            <Button variant="outline" size="sm" className="bg-transparent">
              Last 6 Months
            </Button>
          </CardHeader>
          <CardContent>
            <div className="flex items-baseline gap-3">
              <span className="text-4xl font-bold">Rs. 8,50,000</span>
              <span className="flex items-center text-sm text-emerald-500">
                <TrendingUp className="mr-1 h-4 w-4" />
                +8.5% growth
              </span>
            </div>
            {/* Chart Placeholder */}
            <div className="mt-6 h-48 w-full rounded-lg bg-gradient-to-t from-primary/20 to-transparent flex items-end px-4">
              <svg viewBox="0 0 400 100" className="w-full h-full">
                <path
                  d="M0,80 Q50,75 100,70 T200,50 T300,30 T400,10"
                  fill="none"
                  stroke="hsl(var(--primary))"
                  strokeWidth="2"
                />
                <circle cx="100" cy="70" r="4" fill="hsl(var(--primary))" />
                <circle cx="200" cy="50" r="4" fill="hsl(var(--primary))" />
                <circle cx="300" cy="30" r="4" fill="hsl(var(--primary))" />
                <circle cx="400" cy="10" r="6" fill="hsl(var(--primary))" />
              </svg>
            </div>
            <div className="mt-4 flex justify-between text-sm text-muted-foreground">
              <span>MAY</span>
              <span>JUN</span>
              <span>JUL</span>
              <span>AUG</span>
              <span>SEP</span>
              <span>OCT</span>
            </div>
          </CardContent>
        </Card>

        {/* Recent Inquiries */}
        <Card>
          <CardHeader className="flex flex-row items-center justify-between">
            <CardTitle>Recent Inquiries</CardTitle>
            <Button variant="link" className="text-primary h-auto p-0">
              View All
            </Button>
          </CardHeader>
          <CardContent className="space-y-4">
            {mockTenantRequests.slice(0, 4).map((request, index) => (
              <div key={request.id} className="flex items-center justify-between rounded-lg border border-border p-3">
                <div className="flex items-center gap-3">
                  <Avatar className="h-10 w-10">
                    <AvatarImage src={request.tenant.avatar || "/placeholder.svg"} />
                    <AvatarFallback>{request.tenant.name.charAt(0)}</AvatarFallback>
                  </Avatar>
                  <div>
                    <p className="font-medium text-sm">{request.tenant.name}</p>
                    <p className="text-xs text-muted-foreground truncate max-w-[120px]">{request.property.name}</p>
                  </div>
                </div>
                {index === 0 ? (
                  <Badge className="bg-emerald-100 text-emerald-700 hover:bg-emerald-100">New</Badge>
                ) : index === 1 ? (
                  <Badge variant="destructive">Pending</Badge>
                ) : index === 3 ? (
                  <Badge variant="secondary">Viewed</Badge>
                ) : (
                  <Button variant="ghost" size="icon" className="h-8 w-8">
                    <Phone className="h-4 w-4" />
                  </Button>
                )}
              </div>
            ))}
          </CardContent>
        </Card>
      </div>

      {/* Properties Overview */}
      <div className="mt-8">
        <div className="flex items-center justify-between">
          <h2 className="text-xl font-semibold">Properties Overview</h2>
          <Button variant="link" className="text-primary h-auto p-0 gap-1">
            View All Properties <ArrowRight className="h-4 w-4" />
          </Button>
        </div>
        <div className="mt-4 grid gap-6 md:grid-cols-3">
          {[
            {
              status: "Occupied",
              image: "modern apartment building exterior green",
              name: "Sunrise Apartments",
              location: "Baneshwor",
            },
            {
              status: "Vacant",
              image: "lakeside cottage nepal mountains",
              name: "Lakeside Cottage",
              location: "Pokhara",
            },
            {
              status: "Occupied",
              image: "traditional nepali house brick exterior",
              name: "Golden Oak Residency",
              location: "Lalitpur",
            },
          ].map((property) => (
            <Card key={property.name} className="overflow-hidden">
              <div className="relative aspect-[16/9]">
                <Image
                  src={`/.jpg?height=200&width=350&query=${property.image}`}
                  alt={property.name}
                  fill
                  className="object-cover"
                />
                <Badge
                  className={`absolute top-3 left-3 ${
                    property.status === "Occupied" ? "bg-emerald-500 text-white" : "bg-amber-500 text-white"
                  }`}
                >
                  {property.status === "Occupied" ? "● Occupied" : "⚠ Vacant"}
                </Badge>
              </div>
              <CardContent className="p-4">
                <h3 className="font-semibold">{property.name}</h3>
                <p className="text-sm text-muted-foreground">{property.location}</p>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </div>
  )
}
