import { Navbar } from "@/components/navbar"
import { Footer } from "@/components/footer"
import { PropertyCard } from "@/components/property-card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Checkbox } from "@/components/ui/checkbox"
import { Slider } from "@/components/ui/slider"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Badge } from "@/components/ui/badge"
import { mockProperties } from "@/lib/mock-data"
import { MapPin, List, Map, ChevronLeft, ChevronRight } from "lucide-react"
import Link from "next/link"

export default function PropertiesPage() {
  return (
    <div className="flex min-h-screen flex-col">
      <Navbar variant="public" showSearch />

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
              <span className="text-foreground">Kathmandu</span>
            </nav>
          </div>
        </div>

        <div className="container mx-auto px-4 py-8">
          <div className="flex gap-8">
            {/* Filters Sidebar */}
            <aside className="hidden w-72 shrink-0 lg:block">
              <div className="sticky top-24 rounded-xl border border-border bg-card p-6">
                <div className="flex items-center justify-between">
                  <h2 className="text-lg font-semibold text-foreground">Filters</h2>
                  <Button variant="link" className="h-auto p-0 text-sm text-primary">
                    Reset All
                  </Button>
                </div>

                {/* Location */}
                <div className="mt-6">
                  <label className="text-sm font-medium text-foreground">Location</label>
                  <div className="mt-2 relative">
                    <MapPin className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
                    <Input type="text" placeholder="Kathmandu" className="pl-10" defaultValue="Kathmandu" />
                  </div>
                </div>

                {/* Price Range */}
                <div className="mt-6">
                  <div className="flex items-center justify-between">
                    <label className="text-sm font-medium text-foreground">Price Range</label>
                    <Badge variant="outline">NPR</Badge>
                  </div>
                  <div className="mt-4">
                    <Slider defaultValue={[15, 80]} max={200} step={5} className="w-full" />
                    <div className="mt-2 flex items-center justify-between text-sm text-muted-foreground">
                      <span>10k</span>
                      <span>200k+</span>
                    </div>
                  </div>
                </div>

                {/* Property Type */}
                <div className="mt-6">
                  <label className="text-sm font-medium text-foreground">Property Type</label>
                  <div className="mt-3 flex flex-wrap gap-2">
                    {["Apartment", "House", "Commercial", "Room"].map((type, index) => (
                      <Button
                        key={type}
                        variant={index === 0 ? "default" : "outline"}
                        size="sm"
                        className="rounded-full"
                      >
                        {type}
                      </Button>
                    ))}
                  </div>
                </div>

                {/* Bedrooms */}
                <div className="mt-6">
                  <label className="text-sm font-medium text-foreground">Bedrooms (BHK)</label>
                  <div className="mt-3 flex gap-2">
                    {["Any", "1", "2", "3", "4+"].map((num, index) => (
                      <Button
                        key={num}
                        variant={index === 2 ? "default" : "outline"}
                        size="sm"
                        className="h-9 w-9 rounded-full p-0"
                      >
                        {num}
                      </Button>
                    ))}
                  </div>
                </div>

                {/* Amenities */}
                <div className="mt-6">
                  <label className="text-sm font-medium text-foreground">Amenities</label>
                  <div className="mt-3 space-y-3">
                    {["Furnished", "Parking Available", "Wi-Fi / Internet", "Pet Friendly"].map((amenity, index) => (
                      <div key={amenity} className="flex items-center gap-2">
                        <Checkbox id={amenity} defaultChecked={index === 1} />
                        <label htmlFor={amenity} className="text-sm text-muted-foreground cursor-pointer">
                          {amenity}
                        </label>
                      </div>
                    ))}
                  </div>
                </div>

                {/* Apply Button */}
                <Button className="mt-8 w-full">Apply Filters</Button>
              </div>
            </aside>

            {/* Results */}
            <div className="flex-1">
              {/* Results Header */}
              <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                  <h1 className="text-2xl font-bold text-foreground">124 rentals in Kathmandu</h1>
                  <p className="text-sm text-muted-foreground">Showing results for 2 BHK, Price 15k-80k</p>
                </div>
                <div className="flex items-center gap-3">
                  <div className="flex rounded-lg border border-border bg-card p-1">
                    <Button variant="ghost" size="sm" className="gap-2">
                      <List className="h-4 w-4" />
                      List
                    </Button>
                    <Button variant="ghost" size="sm" className="gap-2">
                      <Map className="h-4 w-4" />
                      Map
                    </Button>
                  </div>
                  <Select defaultValue="recommended">
                    <SelectTrigger className="w-[160px]">
                      <SelectValue placeholder="Sort by" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="recommended">Recommended</SelectItem>
                      <SelectItem value="price-low">Price: Low to High</SelectItem>
                      <SelectItem value="price-high">Price: High to Low</SelectItem>
                      <SelectItem value="newest">Newest First</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </div>

              {/* Property Grid */}
              <div className="mt-6 grid gap-6 sm:grid-cols-2 xl:grid-cols-3">
                {mockProperties.map((property) => (
                  <PropertyCard key={property.id} property={property} />
                ))}
              </div>

              {/* Pagination */}
              <div className="mt-12 flex items-center justify-center gap-2">
                <Button variant="outline" size="icon" className="h-10 w-10 rounded-full bg-transparent">
                  <ChevronLeft className="h-4 w-4" />
                </Button>
                {[1, 2, 3].map((page) => (
                  <Button
                    key={page}
                    variant={page === 1 ? "default" : "outline"}
                    size="icon"
                    className="h-10 w-10 rounded-full"
                  >
                    {page}
                  </Button>
                ))}
                <span className="px-2 text-muted-foreground">...</span>
                <Button variant="outline" size="icon" className="h-10 w-10 rounded-full bg-transparent">
                  8
                </Button>
                <Button variant="outline" size="icon" className="h-10 w-10 rounded-full bg-transparent">
                  <ChevronRight className="h-4 w-4" />
                </Button>
              </div>
            </div>
          </div>
        </div>
      </main>

      <Footer />
    </div>
  )
}
