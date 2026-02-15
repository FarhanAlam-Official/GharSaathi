"use client"

import Link from "next/link"
import Image from "next/image"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import { Heart, MapPin, Bed, Bath, Maximize, Star, Check } from "lucide-react"
import type { Property } from "@/types/property.types"

interface PropertyCardProps {
  property: Property
  variant?: "default" | "saved" | "landlord"
  onSave?: () => void
  onRemove?: () => void
}

export function PropertyCard({ property, variant = "default", onSave }: PropertyCardProps) {
  const primaryImage = property.images?.find(img => img.isPrimary) || property.images?.[0]
  const imageUrl = primaryImage?.imageUrl || '/placeholder.svg'
  
  return (
    <Link href={`/properties/${property.id}`}>
      <Card className="group overflow-hidden border-border hover:shadow-lg transition-shadow duration-300">
        <div className="relative aspect-[4/3] overflow-hidden">
          <Image
            src={imageUrl}
            alt={property.title}
            fill
            className="object-cover transition-transform duration-300 group-hover:scale-105"
          />

          {/* Badges */}
          <div className="absolute top-3 left-3 flex flex-wrap gap-2">
            <Badge className="bg-emerald-500 text-white hover:bg-emerald-600">
              <Check className="h-3 w-3 mr-1" />
              Verified
            </Badge>
            {property.status === 'AVAILABLE' && (
              <Badge className="bg-green-500 text-white">Available</Badge>
            )}
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
                <span className="truncate">{property.area}, {property.city}</span>
              </div>
            </div>
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
              <span>{property.propertyArea} sq ft</span>
            </div>
          </div>

          {/* Amenities */}
          <div className="mt-3 flex flex-wrap gap-2 text-xs">
            {property.furnished && (
              <Badge variant="secondary" className="text-xs">Furnished</Badge>
            )}
            {property.parkingAvailable && (
              <Badge variant="secondary" className="text-xs">Parking</Badge>
            )}
          </div>

          {/* Price and Action */}
          <div className="mt-4 flex items-center justify-between">
            <div>
              <span className="text-lg font-bold text-primary">NPR {property.price.toLocaleString()}</span>
              <span className="text-sm text-muted-foreground"> / month</span>
            </div>
          </div>

          {/* Landlord Info */}
          {property.landlord && (
            <div className="mt-3 pt-3 border-t text-xs text-muted-foreground">
              Listed by <span className="font-medium">{property.landlord.fullName}</span>
            </div>
          )}
        </CardContent>
      </Card>
    </Link>
  )
}
