'use client'

import { useState, useEffect } from 'react'
import { useParams, useRouter } from 'next/navigation'
import Image from 'next/image'
import Link from 'next/link'
import type React from "react"
import { Navbar } from "@/components/navbar"
import { Footer } from "@/components/footer"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Skeleton } from '@/components/ui/skeleton'
import { Alert, AlertDescription } from '@/components/ui/alert'
import {
  MapPin,
  Bed,
  Bath,
  Maximize,
  Heart,
  Share2,
  ShieldCheck,
  Phone,
  MessageCircle,
  Calendar,
  ChevronRight,
  Wifi,
  Car,
  Sofa,
  PawPrint,
  AlertCircle,
  ChevronLeft,
  User,
  CheckCircle2,
  Home,
  Play,
} from "lucide-react"
import { propertyService } from '@/lib/services/property.service'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { Property } from '@/types/property.types'

export default function PropertyDetailPage() {
  const params = useParams()
  const router = useRouter()
  const propertyId = parseInt(params.id as string)
  
  const [property, setProperty] = useState<Property | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [selectedImageIndex, setSelectedImageIndex] = useState(0)

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        setIsLoading(true)
        setError(null)
        const data = await propertyService.getPropertyById(propertyId)
        setProperty(data)
      } catch (err) {
        const apiError = handleAPIError(err)
        setError(apiError.message)
      } finally {
        setIsLoading(false)
      }
    }

    if (propertyId) {
      fetchProperty()
    }
  }, [propertyId])

  if (isLoading) {
    return (
      <div className="flex min-h-screen flex-col">
        <Navbar variant="public" />
        <main className="flex-1 container mx-auto px-4 py-8">
          <Skeleton className="h-[500px] w-full rounded-lg mb-8" />
          <div className="grid gap-8 lg:grid-cols-3">
            <div className="lg:col-span-2">
              <Skeleton className="h-[300px] w-full rounded-lg" />
            </div>
            <Skeleton className="h-[400px] w-full rounded-lg" />
          </div>
        </main>
        <Footer />
      </div>
    )
  }

  if (error || !property) {
    return (
      <div className="flex min-h-screen flex-col">
        <Navbar variant="public" />
        <main className="flex-1 container mx-auto px-4 py-8">
          <Alert variant="destructive">
            <AlertCircle className="h-4 w-4" />
            <AlertDescription>{error || 'Property not found'}</AlertDescription>
          </Alert>
          <Button onClick={() => router.push('/properties')} className="mt-4">
            <ChevronLeft className="mr-2 h-4 w-4" />
            Back to Properties
          </Button>
        </main>
        <Footer />
      </div>
    )
  }

  const selectedImage = property.images[selectedImageIndex] || property.images[0]

  return (
    <div className="flex min-h-screen flex-col">
      <Navbar variant="public" />

      <main className="flex-1">
        {/* Breadcrumb */}
        <div className="border-b border-border bg-card">
          <div className="container mx-auto px-4 py-3">
            <nav className="flex items-center gap-2 text-sm text-muted-foreground">
              <Link href="/" className="hover:text-foreground">Home</Link>
              <ChevronRight className="h-4 w-4" />
              <Link href="/properties" className="hover:text-foreground">Properties</Link>
              <ChevronRight className="h-4 w-4" />
              <span className="text-foreground">{property.title}</span>
            </nav>
          </div>
        </div>

        <div className="container mx-auto px-4 py-8">
          <div className="grid gap-8 lg:grid-cols-3">
            {/* Main Content */}
            <div className="lg:col-span-2 space-y-8">
              {/* Image Gallery */}
              <div className="space-y-4">
                <div className="relative aspect-[16/9] overflow-hidden rounded-2xl bg-black">
                  {selectedImage ? (
                    selectedImage.mediaType === 'VIDEO' ? (
                      <video
                        src={selectedImage.fileUrl}
                        controls
                        className="w-full h-full object-contain"
                        autoPlay
                      />
                    ) : (
                      <Image src={selectedImage.fileUrl} alt={property.title} fill className="object-cover" />
                    )
                  ) : (
                    <div className="w-full h-full bg-muted flex items-center justify-center">
                      <Home className="h-24 w-24 text-muted-foreground" />
                    </div>
                  )}
                  <div className="absolute top-4 left-4 flex gap-2">
                    <Badge className="bg-emerald-500 text-white">
                      <CheckCircle2 className="h-3 w-3 mr-1" />
                      Verified
                    </Badge>
                    <Badge className="bg-green-500 text-white">{property.status}</Badge>
                    {selectedImage?.mediaType === 'VIDEO' && (
                      <Badge className="bg-purple-500 text-white">
                        <Play className="h-3 w-3 mr-1" />
                        Video
                      </Badge>
                    )}
                  </div>
                  <div className="absolute top-4 right-4 flex gap-2">
                    <Button variant="secondary" size="icon" className="h-10 w-10 rounded-full">
                      <Heart className="h-5 w-5" />
                    </Button>
                    <Button variant="secondary" size="icon" className="h-10 w-10 rounded-full">
                      <Share2 className="h-5 w-5" />
                    </Button>
                  </div>
                </div>

                {/* Thumbnails */}
                {property.images.length > 1 && (
                  <div className="grid grid-cols-5 gap-2">
                    {property.images.slice(0, 5).map((image, index) => (
                      <button
                        key={image.id}
                        onClick={() => setSelectedImageIndex(index)}
                        className={`relative aspect-square overflow-hidden rounded-lg border-2 transition-all ${
                          selectedImageIndex === index ? 'border-primary' : 'border-transparent hover:border-muted-foreground'
                        }`}
                      >
                        {image.mediaType === 'VIDEO' ? (
                          <div className="w-full h-full bg-black flex items-center justify-center">
                            <Play className="h-8 w-8 text-white" />
                          </div>
                        ) : (
                          <Image src={image.fileUrl} alt={`${property.title} - ${index + 1}`} fill className="object-cover" />
                        )}
                      </button>
                    ))}
                  </div>
                )}
              </div>
              {/* Property Info */}
              <div>
                <div className="flex items-start justify-between gap-4">
                  <div className="flex-1">
                    <h1 className="text-2xl font-bold text-foreground lg:text-3xl">{property.title}</h1>
                    <div className="mt-2 flex items-center gap-2 text-muted-foreground">
                      <MapPin className="h-4 w-4" />
                      <span>{property.area}, {property.city}</span>
                    </div>
                  </div>
                  <div className="text-right">
                    <p className="text-3xl font-bold text-primary">
                      NPR {property.price.toLocaleString()}
                      <span className="text-base font-normal text-muted-foreground">/month</span>
                    </p>
                    <p className="text-sm text-muted-foreground">+ NPR {property.securityDeposit.toLocaleString()} deposit</p>
                  </div>
                </div>

                {/* Quick Stats */}
                <div className="mt-6 flex flex-wrap gap-4">
                  <div className="flex items-center gap-3 rounded-lg border border-border bg-card px-4 py-3">
                    <Bed className="h-5 w-5 text-muted-foreground" />
                    <div>
                      <p className="text-lg font-semibold">{property.bedrooms}</p>
                      <p className="text-xs text-muted-foreground">Bedrooms</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-3 rounded-lg border border-border bg-card px-4 py-3">
                    <Bath className="h-5 w-5 text-muted-foreground" />
                    <div>
                      <p className="text-lg font-semibold">{property.bathrooms}</p>
                      <p className="text-xs text-muted-foreground">Bathrooms</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-3 rounded-lg border border-border bg-card px-4 py-3">
                    <Maximize className="h-5 w-5 text-muted-foreground" />
                    <div>
                      <p className="text-lg font-semibold">{property.propertyArea}</p>
                      <p className="text-xs text-muted-foreground">sq ft</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-3 rounded-lg border border-border bg-card px-4 py-3">
                    <Calendar className="h-5 w-5 text-muted-foreground" />
                    <div>
                      <p className="text-sm font-semibold">{new Date(property.availableFrom).toLocaleDateString()}</p>
                      <p className="text-xs text-muted-foreground">Available</p>
                    </div>
                  </div>
                </div>
              </div>

              {/* Description */}
              <Card>
                <CardHeader>
                  <CardTitle>Description</CardTitle>
                </CardHeader>
                <CardContent>
                  <p className="text-muted-foreground leading-relaxed whitespace-pre-line">
                    {property.description}
                  </p>
                </CardContent>
              </Card>

              {/* Amenities */}
              <Card>
                <CardHeader>
                  <CardTitle>Features & Amenities</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="grid grid-cols-2 gap-3">
                    {property.furnished && (
                      <div className="flex items-center gap-2">
                        <Sofa className="h-5 w-5 text-primary" />
                        <span>Furnished</span>
                      </div>
                    )}
                    {property.parkingAvailable && (
                      <div className="flex items-center gap-2">
                        <Car className="h-5 w-5 text-primary" />
                        <span>Parking</span>
                      </div>
                    )}
                    <div className="flex items-center gap-2">
                      <Wifi className="h-5 w-5 text-primary" />
                      <span>Wi-Fi Ready</span>
                    </div>
                    <div className="flex items-center gap-2">
                      <ShieldCheck className="h-5 w-5 text-primary" />
                      <span>24/7 Security</span>
                    </div>
                  </div>
                </CardContent>
              </Card>

              {/* Location */}
              <Card>
                <CardHeader>
                  <CardTitle>Location</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="space-y-4">
                    <div className="space-y-2 text-sm">
                      <div className="flex justify-between">
                        <span className="text-muted-foreground">Address:</span>
                        <span className="font-medium text-right">{property.address}</span>
                      </div>
                      <div className="flex justify-between">
                        <span className="text-muted-foreground">Area:</span>
                        <span className="font-medium">{property.area}</span>
                      </div>
                      <div className="flex justify-between">
                        <span className="text-muted-foreground">City:</span>
                        <span className="font-medium">{property.city}</span>
                      </div>
                    </div>
                    
                    {/* Google Maps Embed */}
                    {property.googleMapsUrl && (
                      <div className="mt-4">
                        <div className="aspect-video w-full overflow-hidden rounded-lg border border-border">
                          <iframe
                            src={property.googleMapsUrl}
                            width="100%"
                            height="100%"
                            style={{ border: 0 }}
                            allowFullScreen
                            loading="lazy"
                            referrerPolicy="no-referrer-when-downgrade"
                            className="w-full h-full"
                          />
                        </div>
                        <a
                          href={property.googleMapsUrl.replace('/embed?', '/search/?')}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="mt-2 inline-flex items-center gap-2 text-sm text-primary hover:underline"
                        >
                          <MapPin className="h-4 w-4" />
                          View on Google Maps
                        </a>
                      </div>
                    )}
                  </div>
                </CardContent>
              </Card>
            </div>

            {/* Sidebar */}
            <div className="space-y-6 lg:sticky lg:top-24 lg:self-start">
              {/* Owner Card */}
              <Card>
                <CardContent className="p-6">
                  <div className="flex items-center gap-4">
                    <Avatar className="h-16 w-16">
                      <AvatarImage src={property.landlord?.avatarUrl || "/placeholder.svg"} />
                      <AvatarFallback>{property.landlord?.fullName?.charAt(0) || "L"}</AvatarFallback>
                    </Avatar>
                    <div className="flex-1">
                      <div className="flex items-center gap-2">
                        <h3 className="font-semibold text-foreground">{property.landlord?.fullName || "Property Owner"}</h3>
                        <ShieldCheck className="h-5 w-5 text-emerald-500" />
                      </div>
                      <p className="text-sm text-muted-foreground">
                        Joined {new Date(property.landlord?.joinedDate || Date.now()).getFullYear()} • {property.landlord?.responseRate || 95}%
                        Response
                      </p>
                    </div>
                  </div>
                  <div className="mt-6 grid gap-3">
                    <Button className="w-full gap-2">
                      <Phone className="h-4 w-4" />
                      Contact Owner
                    </Button>
                    <Button variant="outline" className="w-full gap-2 bg-transparent">
                      <MessageCircle className="h-4 w-4" />
                      Send Message
                    </Button>
                  </div>
                </CardContent>
              </Card>

              {/* Schedule Visit Card */}
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center gap-2">
                    <Calendar className="h-5 w-5" />
                    Schedule a Visit
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <p className="text-sm text-muted-foreground mb-4">
                    Choose a convenient time to view this property in person or virtually.
                  </p>
                  <Button className="w-full">Book Visit</Button>
                </CardContent>
              </Card>

              {/* Safety Tips */}
              <Card className="bg-amber-50 border-amber-200 dark:bg-amber-950/20 dark:border-amber-900">
                <CardContent className="p-6">
                  <h3 className="font-semibold text-foreground flex items-center gap-2">
                    <ShieldCheck className="h-5 w-5 text-amber-600" />
                    Safety Tips
                  </h3>
                  <ul className="mt-3 space-y-2 text-sm text-muted-foreground">
                    <li>• Always verify the owner&apos;s identity</li>
                    <li>• Visit the property before paying</li>
                    <li>• Never pay in cash without receipts</li>
                    <li>• Read the agreement carefully</li>
                  </ul>
                </CardContent>
              </Card>
            </div>
          </div>
        </div>
      </main>

      <Footer />
    </div>
  )
}
