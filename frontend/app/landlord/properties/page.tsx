"use client"

import { Card, CardContent } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"
import {
  Search,
  Plus,
  Building2,
  TrendingUp,
  AlertCircle,
  Pencil,
  Eye,
  Rocket,
  MessageSquare,
  MapPin,
} from "lucide-react"
import Image from "next/image"

const landlordProperties = [
  {
    id: "1",
    title: "Sunrise Apartments, Unit 4B",
    location: "Baneshwor, Kathmandu",
    price: 45000,
    status: "occupied",
    image: "/modern-apartment-building-sunrise.jpg",
    tenant: { name: "Sita Adhikari", initials: "SA", leaseEnd: "Dec 2024" },
  },
  {
    id: "2",
    title: "Lakeside Cottage",
    location: "Lakeside, Pokhara",
    price: 32000,
    status: "vacant",
    image: "/lakeside-cottage-nepal.jpg",
    listedDays: 2,
    views: 14,
  },
  {
    id: "3",
    title: "Golden Oak Residency",
    location: "Jhamsikhel, Lalitpur",
    price: 60000,
    status: "occupied",
    image: "/modern-residency-building.jpg",
    tenant: { name: "Rajesh Karki", initials: "RK", leaseEnd: "Mar 2025" },
  },
]

export default function LandlordPropertiesPage() {
  return (
    <div className="flex-1 bg-[#0f172a] text-slate-100 p-8">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold">My Properties</h1>
          <p className="mt-1 text-slate-400">Manage listings, check status, and edit details.</p>
        </div>
        <Button className="gap-2 bg-primary hover:bg-primary/90">
          <Plus className="h-4 w-4" />
          Add New Property
        </Button>
      </div>

      {/* Stats */}
      <div className="mt-8 grid gap-6 md:grid-cols-3">
        <Card className="bg-slate-800/50 border-slate-700">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-slate-400">Total Properties</p>
              <p className="mt-2 text-4xl font-bold">12</p>
              <p className="mt-1 text-sm text-emerald-400 flex items-center gap-1">
                <TrendingUp className="h-3 w-3" /> +1 since last month
              </p>
            </div>
            <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-slate-700">
              <Building2 className="h-7 w-7 text-slate-300" />
            </div>
          </CardContent>
        </Card>

        <Card className="bg-slate-800/50 border-slate-700">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-slate-400">Occupancy Rate</p>
              <p className="mt-2 text-4xl font-bold">92%</p>
              <p className="mt-1 text-sm text-emerald-400 flex items-center gap-1">
                <TrendingUp className="h-3 w-3" /> 2.5% vs local average
              </p>
            </div>
            <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-emerald-500/20">
              <div className="text-emerald-400">●</div>
            </div>
          </CardContent>
        </Card>

        <Card className="bg-slate-800/50 border-slate-700">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-slate-400">Pending Requests</p>
              <p className="mt-2 text-4xl font-bold">3</p>
              <p className="mt-1 text-sm text-red-400 flex items-center gap-1">
                <AlertCircle className="h-3 w-3" /> High - Action required
              </p>
            </div>
            <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-red-500/20">
              <AlertCircle className="h-7 w-7 text-red-400" />
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Filters */}
      <div className="mt-8 flex flex-col gap-4 sm:flex-row sm:items-center">
        <div className="relative flex-1 max-w-md">
          <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500" />
          <Input
            type="search"
            placeholder="Search properties by name, location..."
            className="pl-10 bg-slate-800/50 border-slate-700 text-slate-100 placeholder:text-slate-500"
          />
        </div>
        <div className="flex gap-2">
          <Button className="bg-primary hover:bg-primary/90">All Properties</Button>
          <Button variant="outline" className="border-slate-700 bg-transparent text-slate-300 hover:bg-slate-800">
            Occupied (11)
          </Button>
          <Button variant="outline" className="border-slate-700 bg-transparent text-slate-300 hover:bg-slate-800">
            Vacant (1)
          </Button>
          <Button variant="outline" className="border-slate-700 bg-transparent text-slate-300 hover:bg-slate-800">
            Maintenance (0)
          </Button>
        </div>
      </div>

      {/* Property Grid */}
      <div className="mt-8 grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        {landlordProperties.map((property) => (
          <Card key={property.id} className="overflow-hidden bg-slate-800/50 border-slate-700">
            <div className="relative aspect-[16/10]">
              <Image src={property.image || "/placeholder.svg"} alt={property.title} fill className="object-cover" />
              <Badge
                className={`absolute top-3 left-3 ${
                  property.status === "occupied" ? "bg-emerald-500 text-white" : "bg-amber-500 text-white"
                }`}
              >
                {property.status === "occupied" ? "● Occupied" : "⚠ Vacant"}
              </Badge>
              <div className="absolute bottom-3 right-3 rounded-lg bg-slate-900/80 px-3 py-1.5 text-sm font-semibold backdrop-blur">
                NPR {property.price.toLocaleString()}/mo
              </div>
            </div>
            <CardContent className="p-4">
              <h3 className="font-semibold text-slate-100">{property.title}</h3>
              <p className="mt-1 flex items-center gap-1 text-sm text-slate-400">
                <MapPin className="h-3.5 w-3.5" />
                {property.location}
              </p>

              {property.status === "occupied" && property.tenant ? (
                <div className="mt-4 rounded-lg bg-slate-700/50 p-3">
                  <p className="text-xs text-slate-400 uppercase">Current Tenant</p>
                  <div className="mt-2 flex items-center justify-between">
                    <div className="flex items-center gap-2">
                      <Avatar className="h-8 w-8">
                        <AvatarFallback className="bg-primary/20 text-primary text-xs">
                          {property.tenant.initials}
                        </AvatarFallback>
                      </Avatar>
                      <div>
                        <p className="text-sm font-medium">{property.tenant.name}</p>
                        <p className="text-xs text-slate-400">Lease ends: {property.tenant.leaseEnd}</p>
                      </div>
                    </div>
                    <Button variant="ghost" size="icon" className="h-8 w-8 text-slate-400">
                      <MessageSquare className="h-4 w-4" />
                    </Button>
                  </div>
                </div>
              ) : (
                <div className="mt-4 rounded-lg bg-amber-500/10 border border-amber-500/20 p-3 text-center">
                  <p className="text-sm text-amber-400">Ready to List</p>
                  <p className="text-xs text-slate-400">
                    Listed {property.listedDays} days ago • {property.views} views
                  </p>
                </div>
              )}

              <div className="mt-4 grid grid-cols-2 gap-3">
                <Button variant="secondary" className="gap-2 bg-slate-700 hover:bg-slate-600 text-slate-100">
                  <Pencil className="h-4 w-4" />
                  Edit
                </Button>
                {property.status === "occupied" ? (
                  <Button className="gap-2 bg-primary hover:bg-primary/90">
                    <Eye className="h-4 w-4" />
                    View
                  </Button>
                ) : (
                  <Button className="gap-2 bg-orange-500 hover:bg-orange-600 text-white">
                    <Rocket className="h-4 w-4" />
                    Boost
                  </Button>
                )}
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  )
}
