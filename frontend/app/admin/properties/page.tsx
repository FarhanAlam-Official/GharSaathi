"use client"

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import {
  Search,
  Bell,
  Moon,
  Filter,
  RefreshCw,
  Clock,
  CheckCircle2,
  XCircle,
  MapPin,
  Bed,
  Bath,
  Maximize,
  MessageCircle,
  X,
  Check,
  ImageIcon,
} from "lucide-react"
import Image from "next/image"

const pendingProperties = [
  {
    id: "1",
    title: "Luxury 2BHK Apartment",
    location: "Bakhundole, Lalitpur (Near Summit Hotel)",
    price: 35000,
    beds: 2,
    baths: 2,
    area: 1200,
    owner: { name: "Ram Sharma", verified: true, joined: "Jan 2023", response: 98, avatar: "" },
    image: "/luxury-apartment-interior-living-room.jpg",
    photos: 5,
  },
  {
    id: "2",
    title: "Sunny Room Near TU",
    location: "Kirtipur, Kathmandu",
    price: 12000,
    beds: 1,
    baths: 1,
    area: 250,
    owner: { name: "Sita Poudel", initials: "SP", verified: false },
    image: "/sunny-room-student-apartment.jpg",
    photos: 3,
  },
  {
    id: "3",
    title: "Flat for Family",
    location: "Baneshwor, Kathmandu",
    price: 45000,
    beds: 3,
    baths: 2,
    area: 1500,
    owner: { name: "Hari Krishna", avatar: "", verified: false },
    image: "/family-flat-living-room-spacious.jpg",
    photos: 7,
  },
]

export default function AdminPropertiesPage() {
  const selectedProperty = pendingProperties[0]

  return (
    <div className="flex h-screen bg-[#0f172a] text-slate-100">
      {/* Left Panel - Queue */}
      <div className="flex w-[400px] flex-col border-r border-slate-700">
        {/* Header */}
        <div className="flex items-center justify-between border-b border-slate-700 p-4">
          <div className="flex items-center gap-2 text-sm text-slate-400">
            <span>Dashboard</span>
            <span>/</span>
            <span>Properties</span>
            <span>/</span>
            <span className="text-slate-100">Approval Queue</span>
          </div>
          <div className="flex items-center gap-2">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500" />
              <Input
                type="search"
                placeholder="Search Listings ID, Owner..."
                className="w-64 pl-10 bg-slate-800 border-slate-700 text-slate-100 placeholder:text-slate-500"
              />
            </div>
            <Button variant="ghost" size="icon" className="text-slate-400 relative">
              <Bell className="h-5 w-5" />
              <span className="absolute -top-1 -right-1 h-4 w-4 rounded-full bg-destructive text-[10px] font-medium text-white flex items-center justify-center">
                3
              </span>
            </Button>
            <Button variant="ghost" size="icon" className="text-slate-400">
              <Moon className="h-5 w-5" />
            </Button>
          </div>
        </div>

        {/* Title and Actions */}
        <div className="p-6">
          <h1 className="text-2xl font-bold">Property Approvals</h1>
          <p className="mt-1 text-sm text-slate-400">Review pending listings from landlords in Kathmandu & Lalitpur.</p>
          <div className="mt-4 flex items-center gap-3">
            <Button variant="outline" className="gap-2 border-slate-700 bg-transparent text-slate-300">
              <Filter className="h-4 w-4" />
              Filter
            </Button>
            <Button className="gap-2 bg-primary hover:bg-primary/90">
              <RefreshCw className="h-4 w-4" />
              Refresh Queue
            </Button>
          </div>
        </div>

        {/* Stats */}
        <div className="grid grid-cols-3 gap-4 px-6">
          <Card className="bg-slate-800/50 border-slate-700">
            <CardContent className="p-4 flex items-center gap-3">
              <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-orange-500/20">
                <Clock className="h-5 w-5 text-orange-400" />
              </div>
              <div>
                <p className="text-xs text-slate-400">Pending Review</p>
                <p className="text-xl font-bold">
                  12 <span className="text-xs text-emerald-400 font-normal">+2 New</span>
                </p>
              </div>
            </CardContent>
          </Card>
          <Card className="bg-slate-800/50 border-slate-700">
            <CardContent className="p-4 flex items-center gap-3">
              <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-emerald-500/20">
                <CheckCircle2 className="h-5 w-5 text-emerald-400" />
              </div>
              <div>
                <p className="text-xs text-slate-400">Approved Today</p>
                <p className="text-xl font-bold">
                  45 <span className="text-xs text-emerald-400 font-normal">+12% vs yest.</span>
                </p>
              </div>
            </CardContent>
          </Card>
          <Card className="bg-slate-800/50 border-slate-700">
            <CardContent className="p-4 flex items-center gap-3">
              <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-red-500/20">
                <XCircle className="h-5 w-5 text-red-400" />
              </div>
              <div>
                <p className="text-xs text-slate-400">Rejected Today</p>
                <p className="text-xl font-bold">
                  3 <span className="text-xs text-red-400 font-normal">-1% vs yest.</span>
                </p>
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Queue */}
        <div className="mt-6 flex-1 overflow-y-auto px-6 pb-6">
          <div className="flex items-center justify-between mb-4">
            <div className="flex items-center gap-2">
              <span className="font-medium">Queue</span>
              <Badge className="bg-primary/20 text-primary">12</Badge>
            </div>
            <Select defaultValue="oldest">
              <SelectTrigger className="w-[120px] bg-transparent border-slate-700 text-slate-300">
                <SelectValue placeholder="Sort" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="oldest">Oldest First</SelectItem>
                <SelectItem value="newest">Newest First</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-3">
            {pendingProperties.map((property, index) => (
              <Card
                key={property.id}
                className={`overflow-hidden cursor-pointer transition-colors ${
                  index === 0 ? "bg-primary/10 border-primary" : "bg-slate-800/50 border-slate-700 hover:bg-slate-800"
                }`}
              >
                <CardContent className="p-3 flex gap-3">
                  <div className="relative h-20 w-20 rounded-lg overflow-hidden shrink-0">
                    <Image
                      src={property.image || "/placeholder.svg"}
                      alt={property.title}
                      fill
                      className="object-cover"
                    />
                    <div className="absolute bottom-1 left-1 flex items-center gap-1 rounded bg-slate-900/80 px-1.5 py-0.5 text-xs">
                      <ImageIcon className="h-3 w-3" />
                      {property.photos}
                    </div>
                  </div>
                  <div className="flex-1 min-w-0">
                    <div className="flex items-center justify-between">
                      <h3 className="font-medium truncate">{property.title}</h3>
                      <Badge className="bg-orange-500/20 text-orange-400 shrink-0">PENDING</Badge>
                    </div>
                    <p className="text-xs text-slate-400 truncate">
                      {property.location} • {property.beds} Bed,{" "}
                      {property.baths === 1 ? "Shared Bath" : property.baths + " Bath"}
                    </p>
                    <div className="mt-2 flex items-center justify-between">
                      <div className="flex items-center gap-2">
                        <Avatar className="h-5 w-5">
                          <AvatarImage src={property.owner.avatar || "/placeholder.svg"} />
                          <AvatarFallback className="text-[10px] bg-slate-600">
                            {property.owner.initials || property.owner.name.charAt(0)}
                          </AvatarFallback>
                        </Avatar>
                        <span className="text-xs text-slate-400">{property.owner.name}</span>
                        {property.owner.verified && <CheckCircle2 className="h-3 w-3 text-primary" />}
                      </div>
                      <span className="font-semibold text-sm">NPR {property.price.toLocaleString()}/mo</span>
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </div>

      {/* Right Panel - Property Detail */}
      <div className="flex-1 overflow-y-auto p-6">
        <div className="max-w-4xl mx-auto">
          {/* Property Image */}
          <div className="relative aspect-video rounded-xl overflow-hidden">
            <Image
              src={selectedProperty.image || "/placeholder.svg"}
              alt={selectedProperty.title}
              fill
              className="object-cover"
            />
            <div className="absolute top-4 left-4">
              <Badge variant="secondary" className="bg-slate-800/80 text-slate-100">
                ID: #GS-2938
              </Badge>
            </div>
            <div className="absolute bottom-4 right-4">
              <Badge variant="secondary" className="bg-slate-800/80 text-slate-100 gap-1">
                <ImageIcon className="h-3 w-3" />
                {selectedProperty.photos} Photos
              </Badge>
            </div>
          </div>

          {/* Property Info */}
          <div className="mt-6 flex items-start justify-between">
            <div>
              <h2 className="text-2xl font-bold">{selectedProperty.title}</h2>
              <p className="mt-2 flex items-center gap-1 text-slate-400">
                <MapPin className="h-4 w-4" />
                {selectedProperty.location}
              </p>
            </div>
            <div className="text-right">
              <p className="text-2xl font-bold text-primary">NPR {selectedProperty.price.toLocaleString() / 1000}k</p>
              <p className="text-sm text-slate-400">per month</p>
            </div>
          </div>

          {/* Quick Stats */}
          <div className="mt-6 grid grid-cols-3 gap-4">
            <Card className="bg-slate-800/50 border-slate-700">
              <CardContent className="p-4 flex items-center justify-center gap-3">
                <Bed className="h-5 w-5 text-slate-400" />
                <div>
                  <p className="text-lg font-semibold">{selectedProperty.beds} Beds</p>
                </div>
              </CardContent>
            </Card>
            <Card className="bg-slate-800/50 border-slate-700">
              <CardContent className="p-4 flex items-center justify-center gap-3">
                <Bath className="h-5 w-5 text-slate-400" />
                <div>
                  <p className="text-lg font-semibold">{selectedProperty.baths} Bath</p>
                </div>
              </CardContent>
            </Card>
            <Card className="bg-slate-800/50 border-slate-700">
              <CardContent className="p-4 flex items-center justify-center gap-3">
                <Maximize className="h-5 w-5 text-slate-400" />
                <div>
                  <p className="text-lg font-semibold">{selectedProperty.area} sqft</p>
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Owner Info */}
          <Card className="mt-6 bg-slate-800/50 border-slate-700">
            <CardContent className="p-4 flex items-center justify-between">
              <div className="flex items-center gap-3">
                <Avatar className="h-12 w-12">
                  <AvatarImage src={selectedProperty.owner.avatar || "/placeholder.svg"} />
                  <AvatarFallback className="bg-amber-500 text-slate-900">
                    {selectedProperty.owner.name.charAt(0)}
                  </AvatarFallback>
                </Avatar>
                <div>
                  <div className="flex items-center gap-2">
                    <p className="font-semibold">{selectedProperty.owner.name}</p>
                    {selectedProperty.owner.verified && <CheckCircle2 className="h-4 w-4 text-primary" />}
                  </div>
                  <p className="text-sm text-slate-400">
                    Joined {selectedProperty.owner.joined} • {selectedProperty.owner.response}% Response
                  </p>
                </div>
              </div>
              <Button variant="ghost" size="icon" className="text-slate-400">
                <MessageCircle className="h-5 w-5" />
              </Button>
            </CardContent>
          </Card>

          {/* Description */}
          <Card className="mt-6 bg-slate-800/50 border-slate-700">
            <CardHeader>
              <CardTitle className="text-base">DESCRIPTION</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-slate-300 leading-relaxed">
                Beautiful and spacious 2BHK apartment located in the heart of Bakhundole, Lalitpur. This property
                features modern amenities, excellent natural lighting, and is conveniently located near Summit Hotel.
                Perfect for families or working professionals looking for a comfortable living space.
              </p>
            </CardContent>
          </Card>

          {/* Action Buttons */}
          <div className="mt-6 flex gap-4">
            <Button
              variant="outline"
              className="flex-1 gap-2 border-red-500/50 text-red-400 hover:bg-red-500/10 bg-transparent"
            >
              <X className="h-4 w-4" />
              Reject
            </Button>
            <Button className="flex-1 gap-2 bg-emerald-600 hover:bg-emerald-700">
              <Check className="h-4 w-4" />
              Approve Property
            </Button>
          </div>
        </div>
      </div>
    </div>
  )
}
