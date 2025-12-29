"use client"

import { Card, CardContent } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Badge } from "@/components/ui/badge"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Checkbox } from "@/components/ui/checkbox"
import { Search, Heart, CheckCircle2, Bed, Bath, Sofa, Car, Star, MapPin, Calendar } from "lucide-react"
import { mockProperties } from "@/lib/mock-data"
import Image from "next/image"
import Link from "next/link"

export default function TenantSavedPage() {
  return (
    <div className="flex-1 p-8">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-foreground">My Saved Homes</h1>
          <p className="mt-1 text-muted-foreground">
            Manage your favorite rental properties. Compare prices and check availability in one place.
          </p>
        </div>
        <div className="flex items-center gap-2">
          <Checkbox id="verified" defaultChecked />
          <label htmlFor="verified" className="flex items-center gap-1.5 text-sm">
            <CheckCircle2 className="h-4 w-4 text-emerald-500" />
            Verified Listings Only
          </label>
        </div>
      </div>

      {/* Search and Sort */}
      <div className="mt-6 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <div className="relative flex-1 max-w-md">
          <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
          <Input
            type="search"
            placeholder="Search by location or property name..."
            className="pl-10 bg-card text-primary"
          />
        </div>
        <div className="flex items-center gap-2">
          <span className="text-sm text-muted-foreground">Sort by:</span>
          <Select defaultValue="newest">
            <SelectTrigger className="w-[150px]">
              <SelectValue placeholder="Sort by" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="newest">Newest Added</SelectItem>
              <SelectItem value="price-low">Price: Low to High</SelectItem>
              <SelectItem value="price-high">Price: High to Low</SelectItem>
            </SelectContent>
          </Select>
        </div>
      </div>

      {/* Saved Properties Grid */}
      <div className="mt-8 grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        {mockProperties.map((property, index) => (
          <Card key={property.id} className="overflow-hidden bg-card">
            <div className="relative aspect-[4/3]">
              <Image src={property.image || "/placeholder.svg"} alt={property.title} fill className="object-cover" />
              {property.verified && (
                <Badge className="absolute top-3 left-3 bg-primary text-white gap-1">
                  <CheckCircle2 className="h-3 w-3" />
                  Verified
                </Badge>
              )}
              {property.isNew && <Badge className="absolute top-3 left-3 bg-emerald-500 text-white">New</Badge>}
              <Button
                variant="ghost"
                size="icon"
                className="absolute top-3 right-3 h-9 w-9 rounded-full bg-white shadow text-red-500"
              >
                <Heart className="h-5 w-5 fill-current" />
              </Button>
              <div className="absolute bottom-0 left-0 right-0 bg-gradient-to-t from-black/70 to-transparent p-4">
                <div className="flex items-center gap-1.5 text-white/80 text-xs">
                  <Calendar className="h-3.5 w-3.5" />
                  Saved{" "}
                  {index === 0
                    ? "2 days ago"
                    : index === 1
                      ? "5 days ago"
                      : `${index + 1} week${index > 0 ? "s" : ""} ago`}
                </div>
              </div>
            </div>
            <CardContent className="p-4">
              <div className="flex items-start justify-between">
                <div>
                  <div className="flex items-center gap-2">
                    <span className="text-lg font-bold text-primary">Rs. {property.price.toLocaleString()}</span>
                    <span className="text-sm text-muted-foreground">/mo</span>
                  </div>
                </div>
                <div className="flex items-center gap-1">
                  <Star className="h-4 w-4 fill-amber-400 text-amber-400" />
                  <span className="text-sm font-medium">{property.rating}</span>
                </div>
              </div>
              <h3 className="mt-2 font-semibold text-foreground">{property.title}</h3>
              <p className="mt-1 flex items-center gap-1 text-sm text-muted-foreground">
                <MapPin className="h-3.5 w-3.5" />
                {property.location}
              </p>

              {/* Property Details */}
              <div className="mt-4 flex items-center gap-3">
                <div className="flex items-center gap-1.5 rounded-lg border border-border px-2 py-1 text-xs">
                  <Bed className="h-3.5 w-3.5 text-muted-foreground" />
                  <span>
                    {property.bedrooms}
                    <br />
                    Bed
                  </span>
                </div>
                <div className="flex items-center gap-1.5 rounded-lg border border-border px-2 py-1 text-xs">
                  <Bath className="h-3.5 w-3.5 text-muted-foreground" />
                  <span>
                    {property.bathrooms}
                    <br />
                    Bath
                  </span>
                </div>
                <div className="flex items-center gap-1.5 rounded-lg border border-border px-2 py-1 text-xs">
                  {property.amenities.includes("Furnished") ? (
                    <>
                      <Sofa className="h-3.5 w-3.5 text-muted-foreground" />
                      <span>Furnished</span>
                    </>
                  ) : property.amenities.includes("Parking") ? (
                    <>
                      <Car className="h-3.5 w-3.5 text-muted-foreground" />
                      <span>Parking</span>
                    </>
                  ) : (
                    <>
                      <Sofa className="h-3.5 w-3.5 text-muted-foreground" />
                      <span>Semi-Furn</span>
                    </>
                  )}
                </div>
              </div>

              <Button className="mt-4 w-full" asChild>
                <Link href={`/properties/${property.id}`}>View Details</Link>
              </Button>
            </CardContent>
          </Card>
        ))}
      </div>

      {/* Compare Banner */}
      <div className="fixed bottom-6 left-1/2 -translate-x-1/2 z-50">
        <div className="flex items-center gap-4 rounded-full bg-slate-800 px-6 py-3 shadow-lg">
          <div className="flex items-center gap-2">
            <div className="flex h-6 w-6 items-center justify-center rounded-full bg-primary text-xs font-medium text-primary-foreground">
              2
            </div>
            <span className="text-sm text-slate-100">Properties selected to compare</span>
          </div>
          <Button variant="ghost" size="sm" className="text-slate-400 hover:text-slate-100">
            Clear
          </Button>
          <Button size="sm" variant="secondary">
            Compare Now
          </Button>
        </div>
      </div>
    </div>
  )
}
