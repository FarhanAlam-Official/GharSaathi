import type React from "react"
import { Navbar } from "@/components/navbar"
import { Footer } from "@/components/footer"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { mockProperties } from "@/lib/mock-data"
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
} from "lucide-react"
import Image from "next/image"
import Link from "next/link"

export default async function PropertyDetailPage({ params }: { params: Promise<{ id: string }> }) {
  const { id } = await params
  const property = mockProperties.find((p) => p.id === id) || mockProperties[0]

  const amenityIcons: Record<string, React.ReactNode> = {
    "Wi-Fi": <Wifi className="h-5 w-5" />,
    Parking: <Car className="h-5 w-5" />,
    Furnished: <Sofa className="h-5 w-5" />,
    "Pet Friendly": <PawPrint className="h-5 w-5" />,
  }

  return (
    <div className="flex min-h-screen flex-col">
      <Navbar variant="public" />

      <main className="flex-1">
        {/* Breadcrumb */}
        <div className="border-b border-border bg-card">
          <div className="container mx-auto px-4 py-3">
            <nav className="flex items-center gap-2 text-sm text-muted-foreground">
              <Link href="/" className="hover:text-foreground">
                Home
              </Link>
              <ChevronRight className="h-4 w-4" />
              <Link href="/properties" className="hover:text-foreground">
                Rentals
              </Link>
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
              <div className="relative aspect-[16/9] overflow-hidden rounded-2xl">
                <Image src={property.image || "/placeholder.svg"} alt={property.title} fill className="object-cover" />
                <div className="absolute top-4 left-4 flex gap-2">
                  {property.verified && <Badge className="bg-emerald-500 text-white">Verified</Badge>}
                  {property.featured && <Badge className="bg-amber-500 text-white">Featured</Badge>}
                </div>
                <div className="absolute top-4 right-4 flex gap-2">
                  <Button variant="secondary" size="icon" className="h-10 w-10 rounded-full">
                    <Heart className="h-5 w-5" />
                  </Button>
                  <Button variant="secondary" size="icon" className="h-10 w-10 rounded-full">
                    <Share2 className="h-5 w-5" />
                  </Button>
                </div>
                <div className="absolute bottom-4 right-4">
                  <Badge variant="secondary" className="gap-1">
                    <span>5 Photos</span>
                  </Badge>
                </div>
              </div>

              {/* Property Info */}
              <div>
                <div className="flex items-start justify-between gap-4">
                  <div>
                    <h1 className="text-2xl font-bold text-foreground lg:text-3xl">{property.title}</h1>
                    <div className="mt-2 flex items-center gap-2 text-muted-foreground">
                      <MapPin className="h-4 w-4" />
                      <span>{property.location}</span>
                    </div>
                  </div>
                  <div className="text-right">
                    <p className="text-3xl font-bold text-primary">
                      NPR {property.price.toLocaleString()}
                      <span className="text-base font-normal text-muted-foreground">/month</span>
                    </p>
                  </div>
                </div>

                {/* Quick Stats */}
                <div className="mt-6 flex flex-wrap gap-4">
                  <div className="flex items-center gap-3 rounded-lg border border-border bg-card px-4 py-3">
                    <Bed className="h-5 w-5 text-muted-foreground" />
                    <div>
                      <p className="text-lg font-semibold">{property.bedrooms}</p>
                      <p className="text-xs text-muted-foreground">Beds</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-3 rounded-lg border border-border bg-card px-4 py-3">
                    <Bath className="h-5 w-5 text-muted-foreground" />
                    <div>
                      <p className="text-lg font-semibold">{property.bathrooms}</p>
                      <p className="text-xs text-muted-foreground">Bath</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-3 rounded-lg border border-border bg-card px-4 py-3">
                    <Maximize className="h-5 w-5 text-muted-foreground" />
                    <div>
                      <p className="text-lg font-semibold">{property.area}</p>
                      <p className="text-xs text-muted-foreground">sqft</p>
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
                  <p className="text-muted-foreground leading-relaxed">
                    Welcome to this beautiful {property.bedrooms} bedroom {property.type} located in the heart of{" "}
                    {property.location}. This property offers modern amenities and a comfortable living space perfect
                    for families or professionals.
                  </p>
                  <p className="mt-4 text-muted-foreground leading-relaxed">
                    The apartment features spacious rooms with excellent natural lighting, a modern kitchen with all
                    essential appliances, and a cozy living area. The location provides easy access to public
                    transportation, markets, and schools.
                  </p>
                </CardContent>
              </Card>

              {/* Amenities */}
              <Card>
                <CardHeader>
                  <CardTitle>Amenities & Features</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="grid grid-cols-2 gap-4 sm:grid-cols-3">
                    {property.amenities.map((amenity) => (
                      <div key={amenity} className="flex items-center gap-3 rounded-lg border border-border p-3">
                        <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-primary/10 text-primary">
                          {amenityIcons[amenity] || <Sofa className="h-5 w-5" />}
                        </div>
                        <span className="text-sm font-medium">{amenity}</span>
                      </div>
                    ))}
                  </div>
                </CardContent>
              </Card>

              {/* Location Map */}
              <Card>
                <CardHeader>
                  <CardTitle>Location</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="aspect-video rounded-lg bg-muted flex items-center justify-center">
                    <p className="text-muted-foreground">Map View - {property.location}</p>
                  </div>
                </CardContent>
              </Card>
            </div>

            {/* Sidebar */}
            <div className="space-y-6">
              {/* Owner Card */}
              <Card>
                <CardContent className="p-6">
                  <div className="flex items-center gap-4">
                    <Avatar className="h-16 w-16">
                      <AvatarImage src={property.landlord?.avatar || "/placeholder.svg"} />
                      <AvatarFallback>{property.landlord?.name?.charAt(0) || "L"}</AvatarFallback>
                    </Avatar>
                    <div className="flex-1">
                      <div className="flex items-center gap-2">
                        <h3 className="font-semibold text-foreground">{property.landlord?.name || "Property Owner"}</h3>
                        {property.landlord?.verified && <ShieldCheck className="h-5 w-5 text-emerald-500" />}
                      </div>
                      <p className="text-sm text-muted-foreground">
                        Joined {property.landlord?.joinedDate || "2023"} • {property.landlord?.responseRate || 95}%
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
