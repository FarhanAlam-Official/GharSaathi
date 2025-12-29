"use client"

import { Card, CardContent } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Search, Bell, FileText, Heart, Calendar, Mail, ArrowRight, AlertCircle, Sparkles, View } from "lucide-react"
import { mockApplications, mockProperties } from "@/lib/mock-data"
import Image from "next/image"

export default function TenantDashboardPage() {
  return (
    <div className="flex-1 p-8">
      {/* Top Bar */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-foreground">Namaste, Aarav! 🙏</h1>
          <p className="mt-1 text-muted-foreground">Here&apos;s what&apos;s happening with your home search.</p>
        </div>
        <div className="flex items-center gap-4">
          <div className="relative flex-1 max-w-xs">
            <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
            <Input type="search" placeholder="Search city, locality..." className="pl-10 bg-card" />
          </div>
          <Button variant="ghost" size="icon" className="relative">
            <Bell className="h-5 w-5" />
          </Button>
          <Avatar className="h-10 w-10">
            <AvatarImage src="/placeholder.svg?height=40&width=40" />
            <AvatarFallback>AS</AvatarFallback>
          </Avatar>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="mt-8 grid gap-6 md:grid-cols-4">
        <Card className="bg-card">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-muted-foreground uppercase">Active Apps</p>
              <p className="mt-2 text-4xl font-bold">2</p>
            </div>
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-orange-100 text-orange-500">
              <FileText className="h-6 w-6" />
            </div>
          </CardContent>
        </Card>

        <Card className="bg-card">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-muted-foreground uppercase">Saved</p>
              <p className="mt-2 text-4xl font-bold">12</p>
            </div>
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-red-100 text-red-500">
              <Heart className="h-6 w-6" />
            </div>
          </CardContent>
        </Card>

        <Card className="bg-card">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-muted-foreground uppercase">Viewings</p>
              <p className="mt-2 text-4xl font-bold">1</p>
            </div>
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-blue-100 text-blue-500">
              <Calendar className="h-6 w-6" />
            </div>
          </CardContent>
        </Card>

        <Card className="bg-card">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-muted-foreground uppercase">Messages</p>
              <p className="mt-2 text-4xl font-bold">0</p>
            </div>
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-purple-100 text-purple-500">
              <Mail className="h-6 w-6" />
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Active Applications */}
      <div className="mt-8">
        <div className="flex items-center justify-between">
          <h2 className="text-xl font-semibold">Active Applications</h2>
          <Button variant="link" className="text-primary h-auto p-0 gap-1">
            View History <ArrowRight className="h-4 w-4" />
          </Button>
        </div>
        <div className="mt-4 space-y-4">
          {mockApplications.map((app) => (
            <Card key={app.id} className="bg-card">
              <CardContent className="p-4 flex items-center gap-4">
                <div className="relative h-20 w-32 rounded-lg overflow-hidden shrink-0">
                  <Image
                    src={app.property.image || "/placeholder.svg"}
                    alt={app.property.title}
                    fill
                    className="object-cover"
                  />
                </div>
                <div className="flex-1 min-w-0">
                  <h3 className="font-semibold truncate">{app.property.title}</h3>
                  <p className="text-sm text-muted-foreground flex items-center gap-1">
                    <span className="text-primary">●</span>
                    {app.property.location} • Rs. {app.property.price.toLocaleString()}/mo
                  </p>
                  <p className="mt-1 text-xs text-muted-foreground">Applied on {app.appliedDate}</p>
                  {app.actionRequired && (
                    <div className="mt-2 flex items-center gap-2 text-xs text-amber-600">
                      <AlertCircle className="h-3.5 w-3.5" />
                      Action Required: {app.actionRequired}
                    </div>
                  )}
                </div>
                <div className="flex items-center gap-3">
                  <Badge
                    className={
                      app.status === "reviewing"
                        ? "bg-orange-100 text-orange-600"
                        : app.status === "approved"
                          ? "bg-emerald-100 text-emerald-600"
                          : "bg-red-100 text-red-600"
                    }
                  >
                    {app.status === "reviewing"
                      ? "Landlord Reviewing"
                      : app.status === "approved"
                        ? "✓ Approved"
                        : "Rejected"}
                  </Badge>
                </div>
                <div className="flex items-center gap-2">
                  <Button variant="outline" size="sm" className="bg-transparent">
                    {app.status === "reviewing" ? "Withdraw" : "Decline"}
                  </Button>
                  <Button size="sm">{app.status === "reviewing" ? "View Details" : "Review Lease"}</Button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>

      {/* Your Shortlist */}
      <div className="mt-8">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <h2 className="text-xl font-semibold">Your Shortlist</h2>
            <Badge variant="secondary">12</Badge>
          </div>
          <div className="flex gap-2">
            <Button variant="outline" size="sm" className="rounded-full bg-primary text-primary-foreground">
              All
            </Button>
            <Button variant="outline" size="sm" className="rounded-full bg-transparent">
              2 Bed
            </Button>
            <Button variant="outline" size="sm" className="rounded-full bg-transparent">
              Under 30k
            </Button>
          </div>
        </div>
        <div className="mt-4 grid gap-6 md:grid-cols-3">
          {mockProperties.slice(0, 3).map((property, index) => (
            <Card key={property.id} className="overflow-hidden bg-card">
              <div className="relative aspect-[4/3]">
                <Image src={property.image || "/placeholder.svg"} alt={property.title} fill className="object-cover" />
                {index === 0 && (
                  <Badge className="absolute top-3 left-3 bg-orange-500 text-white gap-1">
                    <Sparkles className="h-3 w-3" />
                    HOT
                  </Badge>
                )}
                {index === 2 && (
                  <Badge className="absolute top-3 left-3 bg-primary text-white gap-1">
                    <View className="h-3 w-3" />
                    3D TOUR
                  </Badge>
                )}
                <Button
                  variant="ghost"
                  size="icon"
                  className="absolute top-3 right-3 h-9 w-9 rounded-full bg-white/80 text-red-500"
                >
                  <Heart className="h-5 w-5 fill-current" />
                </Button>
                <div className="absolute bottom-3 right-3 rounded-lg bg-foreground/80 px-3 py-1 text-sm font-semibold text-background">
                  Rs. {property.price.toLocaleString()}/mo
                </div>
              </div>
              <CardContent className="p-4">
                <h3 className="font-semibold">{property.title}</h3>
                <p className="text-sm text-muted-foreground">{property.location}</p>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </div>
  )
}
