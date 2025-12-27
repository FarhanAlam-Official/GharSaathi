import { Navbar } from "@/components/navbar"
import { Footer } from "@/components/footer"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Badge } from "@/components/ui/badge"
import { Card, CardContent } from "@/components/ui/card"
import { PropertyCard } from "@/components/property-card"
import { mockProperties } from "@/lib/mock-data"
import { Search, MapPin, ArrowRight, CheckCircle2, ShieldCheck, Eye, Banknote, Star } from "lucide-react"
import Link from "next/link"
import Image from "next/image"

export default function LandingPage() {
  const featuredProperties = mockProperties.slice(0, 4)

  return (
    <div className="flex min-h-screen flex-col">
      <Navbar variant="public" />

      <main className="flex-1">
        {/* Hero Section */}
        <section className="relative overflow-hidden bg-gradient-to-br from-primary/5 via-background to-background py-16 lg:py-24">
          <div className="container mx-auto px-4">
            <div className="grid items-center gap-12 lg:grid-cols-2">
              <div className="max-w-xl">
                <Badge variant="secondary" className="mb-4 rounded-full px-4 py-1.5">
                  <CheckCircle2 className="mr-1.5 h-3.5 w-3.5 text-primary" />
                  TRUSTED BY 10K+ RENTERS
                </Badge>
                <h1 className="text-4xl font-bold tracking-tight text-foreground sm:text-5xl lg:text-6xl">
                  Find your next <span className="text-primary">Ghar</span>
                  <br />
                  with confidence.
                </h1>
                <p className="mt-6 text-lg text-muted-foreground">
                  GharSaathi connects you with verified listings and trusted landlords. Experience a seamless rental
                  journey with our modern handshake guarantee.
                </p>

                {/* Search Box */}
                <div className="mt-8 flex flex-col gap-4 sm:flex-row">
                  <div className="relative flex-1">
                    <MapPin className="absolute left-4 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                    <Input
                      type="text"
                      placeholder="Search by city, neighborhood..."
                      className="h-14 rounded-xl pl-12 pr-4 text-base"
                    />
                  </div>
                  <Button size="lg" className="h-14 rounded-xl px-8" asChild>
                    <Link href="/properties">
                      <Search className="mr-2 h-5 w-5" />
                      Search
                    </Link>
                  </Button>
                </div>

                {/* Trust Indicators */}
                <div className="mt-8 flex items-center gap-6">
                  <div className="flex -space-x-3">
                    {[1, 2, 3, 4].map((i) => (
                      <div
                        key={i}
                        className="h-10 w-10 rounded-full border-2 border-background bg-muted overflow-hidden"
                      >
                        <Image
                          src={`/placeholder.svg?height=40&width=40&query=nepali person ${i}`}
                          alt="User"
                          width={40}
                          height={40}
                          className="object-cover"
                        />
                      </div>
                    ))}
                  </div>
                  <div>
                    <div className="flex items-center gap-1">
                      {[1, 2, 3, 4, 5].map((i) => (
                        <Star key={i} className="h-4 w-4 fill-amber-400 text-amber-400" />
                      ))}
                    </div>
                    <p className="text-sm text-muted-foreground">Trusted by happy tenants</p>
                  </div>
                </div>
              </div>

              {/* Hero Image */}
              <div className="relative hidden lg:block">
                <div className="relative aspect-[4/3] overflow-hidden rounded-2xl">
                  <Image
                    src="/placeholder.svg?height=500&width=600"
                    alt="Modern apartment"
                    fill
                    className="object-cover"
                  />
                  {/* Verified Owner Badge */}
                  <div className="absolute top-4 right-4 flex items-center gap-2 rounded-full bg-background/95 px-4 py-2 shadow-lg backdrop-blur">
                    <ShieldCheck className="h-5 w-5 text-emerald-500" />
                    <div>
                      <p className="text-xs text-muted-foreground">STATUS</p>
                      <p className="text-sm font-semibold">Verified Owner</p>
                    </div>
                  </div>
                </div>
                {/* Floating Card */}
                <div className="absolute -bottom-6 -left-6 rounded-xl bg-background p-4 shadow-xl">
                  <div className="flex items-center gap-3">
                    <div className="flex h-12 w-12 items-center justify-center rounded-lg bg-emerald-100">
                      <CheckCircle2 className="h-6 w-6 text-emerald-600" />
                    </div>
                    <div>
                      <p className="text-sm font-medium text-foreground">Verified Owner</p>
                      <div className="mt-1 h-2 w-32 rounded-full bg-muted">
                        <div className="h-2 w-[85%] rounded-full bg-primary" />
                      </div>
                      <p className="mt-1 text-xs text-muted-foreground">Profile completeness</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>

        {/* Stats Section */}
        <section className="border-y border-border bg-muted/30 py-12">
          <div className="container mx-auto px-4">
            <div className="grid grid-cols-2 gap-8 md:grid-cols-4">
              <div className="text-center">
                <p className="text-3xl font-bold text-primary">2.5k+</p>
                <p className="mt-1 text-sm font-medium uppercase tracking-wider text-muted-foreground">
                  Active Listings
                </p>
              </div>
              <div className="text-center">
                <p className="text-3xl font-bold text-primary">50+</p>
                <p className="mt-1 text-sm font-medium uppercase tracking-wider text-muted-foreground">
                  Cities Covered
                </p>
              </div>
              <div className="text-center">
                <p className="text-3xl font-bold text-primary">10k+</p>
                <p className="mt-1 text-sm font-medium uppercase tracking-wider text-muted-foreground">Happy Tenants</p>
              </div>
              <div className="text-center">
                <p className="text-3xl font-bold text-primary">0%</p>
                <p className="mt-1 text-sm font-medium uppercase tracking-wider text-muted-foreground">
                  Brokerage Fees
                </p>
              </div>
            </div>
          </div>
        </section>

        {/* Featured Categories */}
        <section className="py-16 lg:py-24">
          <div className="container mx-auto px-4">
            <h2 className="text-3xl font-bold text-foreground">Featured Categories</h2>
            <div className="mt-8 grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
              {[
                { name: "Apartments", count: 1230, image: "modern apartment building exterior" },
                { name: "Houses", count: 840, image: "nepali house traditional modern" },
                { name: "Shared Rooms", count: 560, image: "shared room interior modern" },
                { name: "Commercial", count: 210, image: "commercial office space modern" },
              ].map((category) => (
                <Link
                  key={category.name}
                  href={`/properties?type=${category.name.toLowerCase()}`}
                  className="group relative aspect-[4/3] overflow-hidden rounded-xl"
                >
                  <Image
                    src={`/placeholder.svg?height=300&width=400&query=${category.image}`}
                    alt={category.name}
                    fill
                    className="object-cover transition-transform duration-300 group-hover:scale-105"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-foreground/80 to-transparent" />
                  <div className="absolute bottom-4 left-4">
                    <h3 className="text-lg font-semibold text-background">{category.name}</h3>
                    <p className="text-sm text-background/80">{category.count.toLocaleString()} listings</p>
                  </div>
                </Link>
              ))}
            </div>
          </div>
        </section>

        {/* Why Choose Section */}
        <section id="how-it-works" className="bg-muted/30 py-16 lg:py-24">
          <div className="container mx-auto px-4">
            <div className="text-center">
              <h2 className="text-3xl font-bold text-foreground">
                Why Choose <span className="text-primary">GharSaathi?</span>
              </h2>
              <p className="mt-4 text-muted-foreground">
                We&apos;re redefining the rental landscape in Nepal by prioritizing trust, transparency, and technology.
              </p>
            </div>

            <div className="mt-12 grid gap-8 md:grid-cols-3">
              <Card className="border-border bg-card">
                <CardContent className="p-8 text-center">
                  <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-2xl bg-primary/10">
                    <Banknote className="h-8 w-8 text-primary" />
                  </div>
                  <h3 className="mt-6 text-xl font-semibold text-foreground">Zero Brokerage</h3>
                  <p className="mt-3 text-muted-foreground">
                    Connect directly with landlords. Save thousands on commission fees and hidden middleman charges.
                  </p>
                </CardContent>
              </Card>

              <Card className="border-border bg-card">
                <CardContent className="p-8 text-center">
                  <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-2xl bg-primary/10">
                    <ShieldCheck className="h-8 w-8 text-primary" />
                  </div>
                  <h3 className="mt-6 text-xl font-semibold text-foreground">Verified Owners</h3>
                  <p className="mt-3 text-muted-foreground">
                    Every listing is physically verified by our team to ensure safety and prevent rental scams.
                  </p>
                </CardContent>
              </Card>

              <Card className="border-border bg-card">
                <CardContent className="p-8 text-center">
                  <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-2xl bg-primary/10">
                    <Eye className="h-8 w-8 text-primary" />
                  </div>
                  <h3 className="mt-6 text-xl font-semibold text-foreground">3D Virtual Tours</h3>
                  <p className="mt-3 text-muted-foreground">
                    Explore properties from the comfort of your couch with our immersive 360-degree virtual tours.
                  </p>
                </CardContent>
              </Card>
            </div>
          </div>
        </section>

        {/* Trending Properties */}
        <section className="py-16 lg:py-24">
          <div className="container mx-auto px-4">
            <div className="flex items-center justify-between">
              <div>
                <h2 className="text-3xl font-bold text-foreground">Trending in Kathmandu</h2>
                <p className="mt-2 text-muted-foreground">Most viewed properties this week</p>
              </div>
              <Button variant="ghost" asChild>
                <Link href="/properties">
                  View All <ArrowRight className="ml-2 h-4 w-4" />
                </Link>
              </Button>
            </div>

            {/* Property Type Tabs */}
            <div className="mt-6 flex flex-wrap gap-2">
              {["All", "Apartments", "Independent House", "Flatmates"].map((type, index) => (
                <Button key={type} variant={index === 0 ? "default" : "outline"} size="sm" className="rounded-full">
                  {type}
                </Button>
              ))}
            </div>

            {/* Property Grid */}
            <div className="mt-8 grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
              {featuredProperties.map((property) => (
                <PropertyCard key={property.id} property={property} />
              ))}
            </div>
          </div>
        </section>
      </main>

      <Footer />
    </div>
  )
}
