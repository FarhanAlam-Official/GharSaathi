'use client'

import { useState, useEffect, useCallback } from 'react'
import { Navbar } from "@/components/navbar"
import { Footer } from "@/components/footer"
import { PropertyCard } from "@/components/property-card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Skeleton } from "@/components/ui/skeleton"
import { Alert, AlertDescription } from "@/components/ui/alert"
import { Badge } from "@/components/ui/badge"
import { Slider } from "@/components/ui/slider"
import { Checkbox } from "@/components/ui/checkbox"
import { MapPin, List, Map, ChevronLeft, ChevronRight, Search, SlidersHorizontal, AlertCircle } from "lucide-react"
import Link from "next/link"
import { propertyService } from '@/lib/services/property.service'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { Property, PropertySearchCriteria } from '@/types/property.types'

export default function PropertiesPage() {
  const [properties, setProperties] = useState<Property[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const [totalProperties, setTotalProperties] = useState(0)
  
  // Search filters
  const [searchKeyword, setSearchKeyword] = useState('')
  const [selectedCity, setSelectedCity] = useState<string>('all')
  const [selectedType, setSelectedType] = useState<string>('all')
  const [selectedBedrooms, setSelectedBedrooms] = useState<number | undefined>()
  const [minPrice, setMinPrice] = useState<number | undefined>()
  const [maxPrice, setMaxPrice] = useState<number | undefined>()
  const [priceRange, setPriceRange] = useState<number[]>([10, 200])
  const [selectedAmenities, setSelectedAmenities] = useState<string[]>([])

  /**
   * Fetch properties
   */
  const fetchProperties = async (page: number = 0) => {
    try {
      setIsLoading(true)
      setError(null)
      
      const response = await propertyService.getAllProperties(page, 12)
      
      setProperties(response.properties)
      setCurrentPage(response.currentPage)
      setTotalPages(response.totalPages)
      setTotalProperties(response.totalProperties)
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }

  /**
   * Search properties
   */
  const handleSearch = useCallback(async () => {
    try {
      setIsLoading(true)
      setError(null)
      
      // Check if any filters are applied
      const hasFilters = searchKeyword || 
                        (selectedCity && selectedCity !== 'all') || 
                        (selectedType && selectedType !== 'all') ||
                        selectedBedrooms !== undefined ||
                        minPrice !== undefined ||
                        maxPrice !== undefined ||
                        selectedAmenities.length > 0

      let response
      
      if (hasFilters) {
        // Use search endpoint with filters
        const criteria: PropertySearchCriteria = {
          keyword: searchKeyword || undefined,
          city: selectedCity && selectedCity !== 'all' ? selectedCity : undefined,
          propertyType: selectedType && selectedType !== 'all' ? selectedType as any : undefined,
          bedrooms: selectedBedrooms,
          minPrice: minPrice,
          maxPrice: maxPrice,
          amenities: selectedAmenities.length > 0 ? selectedAmenities : undefined,
          page: 0,
          size: 12,
          sortBy: 'createdAt',
          sortDirection: 'DESC',
        }
        
        try {
          response = await propertyService.searchProperties(criteria)
        } catch (searchError) {
          // Fallback: If search fails, get all and filter client-side
          console.warn('Search endpoint failed, using client-side filtering')
          const allProperties = await propertyService.getAllProperties(0, 100)
          
          // Client-side filtering
          let filtered = allProperties.properties
          
          if (searchKeyword) {
            const keyword = searchKeyword.toLowerCase()
            filtered = filtered.filter(p => 
              p.title.toLowerCase().includes(keyword) ||
              p.area.toLowerCase().includes(keyword) ||
              p.city.toLowerCase().includes(keyword)
            )
          }
          
          if (selectedCity && selectedCity !== 'all') {
            filtered = filtered.filter(p => p.city === selectedCity)
          }
          
          if (selectedType && selectedType !== 'all') {
            filtered = filtered.filter(p => p.propertyType === selectedType)
          }
          
          if (selectedBedrooms !== undefined) {
            filtered = filtered.filter(p => p.bedrooms >= selectedBedrooms)
          }
          
          if (minPrice !== undefined) {
            filtered = filtered.filter(p => p.price >= minPrice)
          }
          
          if (maxPrice !== undefined) {
            filtered = filtered.filter(p => p.price <= maxPrice)
          }
          
          // Paginate client-side
          const pageSize = 12
          const totalPages = Math.ceil(filtered.length / pageSize)
          const startIndex = 0
          const endIndex = pageSize
          
          response = {
            properties: filtered.slice(startIndex, endIndex),
            currentPage: 0,
            totalPages: totalPages,
            totalProperties: filtered.length,
            pageSize: pageSize
          }
        }
      } else {
        // No filters, use regular getAllProperties
        response = await propertyService.getAllProperties(0, 12)
      }
      
      setProperties(response.properties)
      setCurrentPage(response.currentPage)
      setTotalPages(response.totalPages)
      setTotalProperties(response.totalProperties)
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }, [searchKeyword, selectedCity, selectedType, selectedBedrooms, minPrice, maxPrice])

  /**
   * Load properties on mount
   */
  useEffect(() => {
    fetchProperties()
  }, [])

  /**
   * Auto-filter when filter values change
   */
  useEffect(() => {
    // Debounce text search to avoid too many API calls
    const timeoutId = setTimeout(() => {
      handleSearch()
    }, searchKeyword ? 500 : 0) // 500ms delay for keyword search, instant for others

    return () => clearTimeout(timeoutId)
  }, [selectedCity, selectedType, selectedBedrooms, minPrice, maxPrice, searchKeyword, handleSearch])

  /**
   * Handle pagination
   */
  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) {
      fetchProperties(page)
      window.scrollTo({ top: 0, behavior: 'smooth' })
    }
  }
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
                  <Button 
                    variant="ghost" 
                    size="sm"
                    className="h-auto px-3 py-1.5 text-sm text-primary hover:text-primary/90 hover:bg-primary/10 transition-colors no-underline"
                    onClick={() => {
                      setSearchKeyword('')
                      setSelectedCity('all')
                      setSelectedType('all')
                      setSelectedBedrooms(undefined)
                      setMinPrice(undefined)
                      setMaxPrice(undefined)
                      setPriceRange([10, 200])
                      setSelectedAmenities([])
                    }}
                    disabled={isLoading}
                  >
                    Reset All
                  </Button>
                </div>

                {/* Location */}
                <div className="mt-6">
                  <label className="text-sm font-medium text-foreground">City</label>
                  <Select value={selectedCity} onValueChange={setSelectedCity}>
                    <SelectTrigger className="mt-2">
                      <SelectValue placeholder="Select city" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">All Cities</SelectItem>
                      <SelectItem value="Kathmandu">Kathmandu</SelectItem>
                      <SelectItem value="Lalitpur">Lalitpur</SelectItem>
                      <SelectItem value="Bhaktapur">Bhaktapur</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                {/* Price Range */}
                <div className="mt-6">
                  <div className="flex items-center justify-between">
                    <label className="text-sm font-medium text-foreground">Price Range</label>
                    <Badge variant="outline" className="text-xs">
                      NPR {priceRange[0]}k - {priceRange[1] >= 200 ? '200k+' : priceRange[1] + 'k'}
                    </Badge>
                  </div>
                  <div className="mt-4">
                    <Slider 
                      value={priceRange} 
                      onValueChange={(value) => {
                        setPriceRange(value)
                        setMinPrice(value[0] * 1000)
                        setMaxPrice(value[1] >= 200 ? undefined : value[1] * 1000)
                      }}
                      min={10}
                      max={200} 
                      step={5} 
                      className="w-full" 
                    />
                    <div className="mt-2 flex items-center justify-between text-sm text-muted-foreground">
                      <span>NPR 10k</span>
                      <span>NPR 200k+</span>
                    </div>
                  </div>
                </div>

                {/* Property Type */}
                <div className="mt-6">
                  <label className="text-sm font-medium text-foreground">Property Type</label>
                  <Select value={selectedType} onValueChange={setSelectedType}>
                    <SelectTrigger className="mt-3">
                      <SelectValue placeholder="Select type" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">All Types</SelectItem>
                      <SelectItem value="APARTMENT">Apartment</SelectItem>
                      <SelectItem value="HOUSE">House</SelectItem>
                      <SelectItem value="ROOM">Room</SelectItem>
                      <SelectItem value="COMMERCIAL">Commercial</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                {/* Bedrooms */}
                <div className="mt-6">
                  <label className="text-sm font-medium text-foreground">Bedrooms (BHK)</label>
                  <div className="mt-3 flex gap-2">
                    {[
                      { label: "Any", value: undefined },
                      { label: "1", value: 1 },
                      { label: "2", value: 2 },
                      { label: "3", value: 3 },
                      { label: "4+", value: 4 }
                    ].map((item) => (
                      <Button
                        key={item.label}
                        variant={selectedBedrooms === item.value ? "default" : "outline"}
                        size="sm"
                        className="h-9 w-9 rounded-full p-0"
                        onClick={() => setSelectedBedrooms(item.value)}
                      >
                        {item.label}
                      </Button>
                    ))}
                  </div>
                </div>

                {/* Amenities */}
                <div className="mt-6">
                  <label className="text-sm font-medium text-foreground">Amenities</label>
                  <div className="mt-3 space-y-3">
                    {[
                      { label: "Furnished", value: "furnished" },
                      { label: "Parking Available", value: "parking" },
                      { label: "Wi-Fi / Internet", value: "wifi" },
                      { label: "Pet Friendly", value: "pet_friendly" }
                    ].map((amenity) => (
                      <div key={amenity.value} className="flex items-center gap-2">
                        <Checkbox 
                          id={amenity.value} 
                          checked={selectedAmenities.includes(amenity.value)}
                          onCheckedChange={(checked) => {
                            if (checked) {
                              setSelectedAmenities([...selectedAmenities, amenity.value])
                            } else {
                              setSelectedAmenities(selectedAmenities.filter(a => a !== amenity.value))
                            }
                          }}
                        />
                        <label htmlFor={amenity.value} className="text-sm text-muted-foreground cursor-pointer">
                          {amenity.label}
                        </label>
                      </div>
                    ))}
                  </div>
                </div>

                {/* Filter Actions */}
                <div className="mt-8 grid gap-2">
                  <Button 
                    variant="outline" 
                    onClick={() => {
                      setSearchKeyword('')
                      setSelectedCity('all')
                      setSelectedType('all')
                      setSelectedBedrooms(undefined)
                      setMinPrice(undefined)
                      setMaxPrice(undefined)
                      setPriceRange([10, 200])
                      setSelectedAmenities([])
                    }}
                    disabled={isLoading}
                    className="w-full"
                  >
                    Clear Filters
                  </Button>
                  <p className="text-xs text-center text-muted-foreground">
                    Filters apply automatically
                  </p>
                </div>
              </div>
            </aside>

            {/* Results */}
            <div className="flex-1">
              {/* Search Bar - Mobile */}
              <div className="mb-6 rounded-lg border bg-card p-4 lg:hidden">
                <div className="grid gap-3">
                  <div className="relative">
                    <Search className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                    <Input
                      type="text"
                      placeholder="Search location..."
                      className="pl-10"
                      value={searchKeyword}
                      onChange={(e) => setSearchKeyword(e.target.value)}
                    />
                  </div>
                  <div className="flex gap-2">
                    <Select value={selectedCity} onValueChange={setSelectedCity}>
                      <SelectTrigger className="flex-1">
                        <SelectValue placeholder="City" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="all">All Cities</SelectItem>
                        <SelectItem value="Kathmandu">Kathmandu</SelectItem>
                        <SelectItem value="Lalitpur">Lalitpur</SelectItem>
                        <SelectItem value="Bhaktapur">Bhaktapur</SelectItem>
                      </SelectContent>
                    </Select>
                    <Select value={selectedType} onValueChange={setSelectedType}>
                      <SelectTrigger className="flex-1">
                        <SelectValue placeholder="Type" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="all">All Types</SelectItem>
                        <SelectItem value="APARTMENT">Apartment</SelectItem>
                        <SelectItem value="HOUSE">House</SelectItem>
                        <SelectItem value="ROOM">Room</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  <Button 
                    variant="outline"
                    onClick={() => {
                      setSearchKeyword('')
                      setSelectedCity('all')
                      setSelectedType('all')
                      setSelectedBedrooms(undefined)
                    }}
                    disabled={isLoading}
                    className="w-full"
                  >
                    <SlidersHorizontal className="mr-2 h-4 w-4" />
                    Clear Filters
                  </Button>
                </div>
              </div>

              {/* Results Header */}
              <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                  {isLoading ? (
                    <Skeleton className="h-8 w-64" />
                  ) : (
                    <>
                      <h1 className="text-2xl font-bold text-foreground">
                        {totalProperties} {totalProperties === 1 ? 'property' : 'properties'} found
                      </h1>
                      <p className="text-sm text-muted-foreground">
                        {selectedCity && `in ${selectedCity}`}
                        {selectedType && ` • ${selectedType}`}
                      </p>
                    </>
                  )}
                </div>
                <div className="flex items-center gap-3">
                  <Select defaultValue="createdAt">
                    <SelectTrigger className="w-[160px]">
                      <SelectValue placeholder="Sort by" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="createdAt">Newest First</SelectItem>
                      <SelectItem value="price">Price: Low to High</SelectItem>
                      <SelectItem value="-price">Price: High to Low</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </div>

              {/* Error State */}
              {error && (
                <Alert variant="destructive" className="mt-6">
                  <AlertCircle className="h-4 w-4" />
                  <AlertDescription>{error}</AlertDescription>
                </Alert>
              )}

              {/* Loading State */}
              {isLoading && (
                <div className="mt-6 grid gap-6 sm:grid-cols-2 xl:grid-cols-3">
                  {[...Array(6)].map((_, i) => (
                    <div key={i} className="space-y-3">
                      <Skeleton className="h-48 w-full rounded-xl" />
                      <Skeleton className="h-4 w-3/4" />
                      <Skeleton className="h-4 w-1/2" />
                    </div>
                  ))}
                </div>
              )}

              {/* Empty State */}
              {!isLoading && !error && properties.length === 0 && (
                <div className="mt-12 text-center">
                  <div className="mx-auto h-24 w-24 rounded-full bg-muted flex items-center justify-center">
                    <Search className="h-12 w-12 text-muted-foreground" />
                  </div>
                  <h3 className="mt-4 text-lg font-semibold">No properties found</h3>
                  <p className="mt-2 text-sm text-muted-foreground">
                    Try adjusting your search criteria
                  </p>
                </div>
              )}

              {/* Property Grid */}
              {!isLoading && !error && properties.length > 0 && (
                <div className="mt-6 grid gap-6 sm:grid-cols-2 xl:grid-cols-3">
                  {properties.map((property) => (
                    <PropertyCard key={property.id} property={property} />
                  ))}
                </div>
              )}

              {/* Pagination */}
              {!isLoading && !error && totalPages > 1 && (
                <div className="mt-12 flex items-center justify-center gap-2">
                  <Button
                    variant="outline"
                    size="icon"
                    className="h-10 w-10 rounded-full"
                    onClick={() => handlePageChange(currentPage - 1)}
                    disabled={currentPage === 0}
                  >
                    <ChevronLeft className="h-4 w-4" />
                  </Button>
                  
                  {[...Array(Math.min(totalPages, 5))].map((_, i) => {
                    const pageNum = currentPage < 3 ? i : currentPage - 2 + i
                    if (pageNum >= totalPages) return null
                    
                    return (
                      <Button
                        key={pageNum}
                        variant={pageNum === currentPage ? "default" : "outline"}
                        size="icon"
                        className="h-10 w-10 rounded-full"
                        onClick={() => handlePageChange(pageNum)}
                      >
                        {pageNum + 1}
                      </Button>
                    )
                  })}
                  
                  {totalPages > 5 && currentPage < totalPages - 3 && (
                    <>
                      <span className="px-2 text-muted-foreground">...</span>
                      <Button
                        variant="outline"
                        size="icon"
                        className="h-10 w-10 rounded-full"
                        onClick={() => handlePageChange(totalPages - 1)}
                      >
                        {totalPages}
                      </Button>
                    </>
                  )}
                  
                  <Button
                    variant="outline"
                    size="icon"
                    className="h-10 w-10 rounded-full"
                    onClick={() => handlePageChange(currentPage + 1)}
                    disabled={currentPage === totalPages - 1}
                  >
                    <ChevronRight className="h-4 w-4" />
                  </Button>
                </div>
              )}
            </div>
          </div>
        </div>
      </main>

      <Footer />
    </div>
  )
}
