"use client"

import Link from "next/link"
import Image from "next/image"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import { Heart, MapPin, Bed, Bath, Maximize, Star } from "lucide-react"
import type { Property } from "@/lib/mock-data"

interface PropertyCardProps {
  property: Property
  variant?: "default" | "saved" | "landlord"
  onSave?: () => void
  onRemove?: () => void
}

export function PropertyCard({ property, variant = "default", onSave }: PropertyCardProps) {
  return (
    <Card className="group overflow-hidden border-border hover:shadow-lg transition-shadow duration-300">
      <div className="relative aspect-[4/3] overflow-hidden">
        <Image
          src={property.image || "/placeholder.svg"}
          alt={property.title}
          fill
          className="object-cover transition-transform duration-300 group-hover:scale-105"
        />

        {/* Badges */}
        <div className="absolute top-3 left-3 flex flex-wrap gap-2">
          {property.verified && <Badge className="bg-emerald-500 text-white hover:bg-emerald-600">Verified</Badge>}
          {property.featured && <Badge className="bg-amber-500 text-white hover:bg-amber-600">Featured</Badge>}
          {property.isNew && <Badge className="bg-emerald-500 text-white hover:bg-emerald-600">New</Badge>}
        </div>

        {/* Save Button */}
        <Button
          variant="ghost"
          size="icon"
          className="absolute top-3 right-3 h-9 w-9 rounded-full bg-white/80 hover:bg-white text-muted-foreground hover:text-destructive"
          onClick={(e) => {
            e.preventDefault()
            onSave?.()
          }}
        >
          <Heart className="h-5 w-5" />
          <span className="sr-only">Save property</span>
        </Button>

        {/* Price Tag */}
        {variant === "landlord" && (
          <div className="absolute bottom-3 right-3 rounded-lg bg-foreground/80 px-3 py-1.5 text-sm font-semibold text-background">
            NPR {property.price.toLocaleString()}/mo
          </div>
        )}
      </div>

      <CardContent className="p-4">
        <div className="flex items-start justify-between gap-2">
          <div className="flex-1 min-w-0">
            <h3 className="font-semibold text-foreground truncate">{property.title}</h3>
            <div className="mt-1 flex items-center gap-1 text-sm text-muted-foreground">
              <MapPin className="h-3.5 w-3.5 shrink-0" />
              <span className="truncate">{property.location}</span>
            </div>
          </div>
          {property.rating && (
            <div className="flex items-center gap-1 text-sm">
              <Star className="h-4 w-4 fill-amber-400 text-amber-400" />
              <span className="font-medium">{property.rating}</span>
            </div>
          )}
        </div>

        {/* Property Details */}
        <div className="mt-4 flex items-center gap-4 text-sm text-muted-foreground">
          <div className="flex items-center gap-1.5">
            <Bed className="h-4 w-4" />
            <span>{property.bedrooms}</span>
          </div>
          <div className="flex items-center gap-1.5">
            <Bath className="h-4 w-4" />
            <span>{property.bathrooms}</span>
          </div>
          <div className="flex items-center gap-1.5">
            <Maximize className="h-4 w-4" />
            <span>{property.area} sqft</span>
          </div>
        </div>

        {/* Price and Action */}
        <div className="mt-4 flex items-center justify-between">
          <div>
            <span className="text-lg font-bold text-primary">NPR {property.price.toLocaleString()}</span>
            <span className="text-sm text-muted-foreground"> / month</span>
          </div>
          <Button variant="outline" size="sm" asChild>
            <Link href={`/properties/${property.id}`}>Details</Link>
          </Button>
        </div>
      </CardContent>
    </Card>
  )
}
