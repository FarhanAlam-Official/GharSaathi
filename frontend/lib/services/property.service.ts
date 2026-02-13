import apiClient from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import type {
  Property,
  PropertyListResponse,
  PropertyDetailResponse,
  PropertySearchCriteria,
  CreatePropertyRequest,
  UpdatePropertyRequest,
  PropertyStatusUpdateRequest,
} from '@/types/property.types'

/**
 * Property Service
 * Handles all property-related API calls
 */
class PropertyService {
  /**
   * Get all properties with pagination
   */
  async getAllProperties(
    page: number = 0,
    size: number = 10,
    sortBy: string = 'createdAt',
    sortDirection: 'ASC' | 'DESC' = 'DESC'
  ): Promise<PropertyListResponse> {
    const response = await apiClient.get<PropertyListResponse>(
      API_ENDPOINTS.PROPERTIES.BASE,
      {
        params: { page, size, sortBy, sortDirection },
      }
    )
    return response.data
  }

  /**
   * Get property by ID
   */
  async getPropertyById(id: number): Promise<PropertyDetailResponse> {
    const response = await apiClient.get<PropertyDetailResponse>(
      API_ENDPOINTS.PROPERTIES.BY_ID(id)
    )
    return response.data
  }

  /**
   * Search properties with filters
   */
  async searchProperties(criteria: PropertySearchCriteria): Promise<PropertyListResponse> {
    const response = await apiClient.post<PropertyListResponse>(
      API_ENDPOINTS.PROPERTIES.SEARCH,
      criteria
    )
    return response.data
  }

  /**
   * Get landlord's properties
   */
  async getMyProperties(
    page: number = 0,
    size: number = 10,
    sortBy: string = 'createdAt',
    sortDirection: 'ASC' | 'DESC' = 'DESC'
  ): Promise<PropertyListResponse> {
    const response = await apiClient.get<PropertyListResponse>(
      API_ENDPOINTS.PROPERTIES.LANDLORD_BASE,
      {
        params: { page, size, sortBy, sortDirection },
      }
    )
    return response.data
  }

  /**
   * Create new property (Landlord only)
   */
  async createProperty(data: CreatePropertyRequest): Promise<Property> {
    const response = await apiClient.post<Property>(
      API_ENDPOINTS.PROPERTIES.LANDLORD_BASE,
      data
    )
    return response.data
  }

  /**
   * Update property (Landlord only)
   */
  async updateProperty(id: number, data: UpdatePropertyRequest): Promise<Property> {
    const response = await apiClient.put<Property>(
      API_ENDPOINTS.PROPERTIES.LANDLORD_BY_ID(id),
      data
    )
    return response.data
  }

  /**
   * Update property status
   */
  async updatePropertyStatus(
    id: number,
    status: PropertyStatusUpdateRequest
  ): Promise<Property> {
    const response = await apiClient.patch<Property>(
      API_ENDPOINTS.PROPERTIES.LANDLORD_STATUS(id),
      status
    )
    return response.data
  }

  /**
   * Delete property (Soft delete - Landlord only)
   */
  async deleteProperty(id: number): Promise<void> {
    await apiClient.delete(API_ENDPOINTS.PROPERTIES.LANDLORD_BY_ID(id))
  }

  /**
   * Force delete property (Admin only)
   */
  async forceDeleteProperty(id: number): Promise<void> {
    await apiClient.delete(API_ENDPOINTS.PROPERTIES.ADMIN_DELETE(id))
  }
}

// Export singleton instance
export const propertyService = new PropertyService()
export default propertyService
