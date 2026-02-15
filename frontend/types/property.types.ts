/**
 * Property types and status enums
 */
export type PropertyType = 'APARTMENT' | 'HOUSE' | 'ROOM' | 'COMMERCIAL' | 'LAND' | 'STUDIO' | 'VILLA'
export type PropertyStatus = 'AVAILABLE' | 'RENTED' | 'MAINTENANCE' | 'UNAVAILABLE' | 'PENDING'

/**
 * Property image
 */
export interface PropertyImage {
  id: number
  filename: string
  fileUrl: string
  mediaType?: 'IMAGE' | 'VIDEO'
  displayOrder: number
  isPrimary: boolean
  uploadedAt: string
}

/**
 * Landlord information (nested in property)
 */
export interface LandlordInfo {
  id: number
  fullName: string
  email: string
  phoneNumber?: string
  avatarUrl?: string
  propertiesCount: number
  responseRate: number
  joinedDate: string
}

/**
 * Complete property entity
 */
export interface Property {
  id: number
  title: string
  description: string
  propertyType: PropertyType
  status: PropertyStatus
  
  // Location
  address: string
  city: string
  area: string
  latitude?: number
  longitude?: number
  googleMapsUrl?: string
  
  // Pricing
  price: number
  securityDeposit: number
  
  // Specifications
  bedrooms: number
  bathrooms: number
  propertyArea: number // in sq ft
  
  // Features
  furnished: boolean
  parkingAvailable: boolean
  amenities: string[]
  
  // Media
  images: PropertyImage[]
  
  // Availability
  availableFrom: string
  
  // Relationships
  landlord: LandlordInfo
  
  // Metadata
  views: number
  savedCount: number
  createdAt: string
  updatedAt: string
}

/**
 * Property list response (paginated)
 */
export interface PropertyListResponse {
  properties: Property[]
  currentPage: number
  totalPages: number
  totalProperties: number
  pageSize: number
}

/**
 * Property detail response
 */
export interface PropertyDetailResponse extends Property {
  similarProperties?: Property[]
}

/**
 * Property search criteria
 */
export interface PropertySearchCriteria {
  keyword?: string
  city?: string
  area?: string
  propertyType?: PropertyType
  status?: PropertyStatus
  minPrice?: number
  maxPrice?: number
  bedrooms?: number
  bathrooms?: number
  furnished?: boolean
  parkingAvailable?: boolean
  amenities?: string[]
  page?: number
  size?: number
  sortBy?: string
  sortDirection?: 'ASC' | 'DESC'
}

/**
 * Create property request
 */
export interface CreatePropertyRequest {
  title: string
  description: string
  propertyType: PropertyType
  address: string
  city: string
  area: string
  latitude?: number
  longitude?: number
  price: number
  securityDeposit: number
  bedrooms: number
  bathrooms: number
  propertyArea: number
  furnished: boolean
  parkingAvailable: boolean
  amenities: string[]
  availableFrom: string
}

/**
 * Update property request
 */
export interface UpdatePropertyRequest extends Partial<CreatePropertyRequest> {
  status?: PropertyStatus
}

/**
 * Property status update request
 */
export interface PropertyStatusUpdateRequest {
  status: PropertyStatus
}
