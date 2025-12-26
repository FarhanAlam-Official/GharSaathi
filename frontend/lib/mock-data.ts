// Mock data for GharSaathi MVP

export interface Property {
  id: string
  title: string
  location: string
  city: string
  price: number
  currency: string
  bedrooms: number
  bathrooms: number
  area: number
  type: "apartment" | "house" | "room" | "commercial"
  status: "available" | "occupied" | "vacant" | "maintenance"
  verified: boolean
  featured: boolean
  isNew: boolean
  rating: number
  image: string
  amenities: string[]
  landlord?: {
    name: string
    avatar: string
    verified: boolean
    joinedDate: string
    responseRate: number
  }
}

export interface User {
  id: string
  name: string
  email: string
  role: "tenant" | "landlord" | "admin"
  avatar: string
  phone?: string
  status: "active" | "pending" | "verified" | "suspended"
  joinedDate: string
  propertiesCount?: number
  activity?: string
}

export interface LeaseInfo {
  id: string
  propertyId: string
  propertyTitle: string
  propertyAddress: string
  tenantName: string
  tenantEmail: string
  startDate: string
  endDate: string
  monthlyRent: number
  status: "active" | "expired" | "pending"
}

export interface RentPayment {
  id: string
  month: string
  amount: number
  status: "paid" | "pending" | "overdue"
  paidDate?: string
}

export interface TenantRequest {
  id: string
  tenant: {
    name: string
    occupation: string
    avatar: string
  }
  property: {
    name: string
    location: string
  }
  moveInDate: string
  trustScore: number
}

export interface Visit {
  id: string
  time: string
  type: "in-person" | "virtual"
  visitor: string
  property: string
  location?: string
  status: "confirmed" | "pending"
}

export interface Application {
  id: string
  property: {
    title: string
    location: string
    price: number
    image: string
  }
  appliedDate: string
  status: "reviewing" | "approved" | "rejected"
  actionRequired?: string
}

// Mock Properties
export const mockProperties: Property[] = [
  {
    id: "1",
    title: "Sunny 2BHK in Lalitpur",
    location: "Jhamsikhel, Lalitpur",
    city: "Kathmandu",
    price: 25000,
    currency: "NPR",
    bedrooms: 2,
    bathrooms: 1,
    area: 950,
    type: "apartment",
    status: "available",
    verified: true,
    featured: false,
    isNew: true,
    rating: 4.8,
    image: "/modern-apartment-living-room-with-green-sofa.jpg",
    amenities: ["Furnished", "Parking", "Wi-Fi"],
    landlord: {
      name: "Ram Sharma",
      avatar: "/nepali-man-professional.jpg",
      verified: true,
      joinedDate: "Jan 2023",
      responseRate: 98,
    },
  },
  {
    id: "2",
    title: "Modern Studio in Lazimpat",
    location: "Lazimpat, Kathmandu",
    city: "Kathmandu",
    price: 18000,
    currency: "NPR",
    bedrooms: 1,
    bathrooms: 1,
    area: 450,
    type: "apartment",
    status: "available",
    verified: true,
    featured: false,
    isNew: false,
    rating: 4.5,
    image: "/modern-studio-apartment-bedroom.jpg",
    amenities: ["Furnished", "Wi-Fi"],
  },
  {
    id: "3",
    title: "Premium Flat in Baluwatar",
    location: "Baluwatar, Kathmandu",
    city: "Kathmandu",
    price: 45000,
    currency: "NPR",
    bedrooms: 3,
    bathrooms: 2,
    area: 1200,
    type: "apartment",
    status: "available",
    verified: true,
    featured: true,
    isNew: false,
    rating: 5.0,
    image: "/luxury-apartment-living-room-city-view.jpg",
    amenities: ["Furnished", "Parking", "Wi-Fi", "Gym"],
  },
  {
    id: "4",
    title: "Budget Room in Koteshwor",
    location: "Koteshwor, Kathmandu",
    city: "Kathmandu",
    price: 12000,
    currency: "NPR",
    bedrooms: 1,
    bathrooms: 1,
    area: 150,
    type: "room",
    status: "available",
    verified: true,
    featured: false,
    isNew: false,
    rating: 4.2,
    image: "/budget-room-apartment-kitchen-yellow.jpg",
    amenities: ["Shared Bathroom"],
  },
  {
    id: "5",
    title: "Full House in Bhaisepati",
    location: "Bhaisepati, Lalitpur",
    city: "Lalitpur",
    price: 75000,
    currency: "NPR",
    bedrooms: 4,
    bathrooms: 3,
    area: 2200,
    type: "house",
    status: "available",
    verified: true,
    featured: false,
    isNew: false,
    rating: 4.9,
    image: "/full-house-garden-exterior-nepal.jpg",
    amenities: ["Furnished", "Parking", "Garden", "Wi-Fi"],
  },
  {
    id: "6",
    title: "Shared Flat in Naxal",
    location: "Naxal, Kathmandu",
    city: "Kathmandu",
    price: 35000,
    currency: "NPR",
    bedrooms: 3,
    bathrooms: 2,
    area: 1100,
    type: "apartment",
    status: "available",
    verified: true,
    featured: false,
    isNew: false,
    rating: 4.0,
    image: "/shared-flat-modern-couch-blue-sofa.jpg",
    amenities: ["Furnished", "Wi-Fi", "Pet Friendly"],
  },
]

// Mock Users
export const mockUsers: User[] = [
  {
    id: "1",
    name: "Rajesh Hamal",
    email: "rajesh@gharsaathi.com",
    role: "landlord",
    avatar: "/nepali-man-business-professional.jpg",
    status: "verified",
    joinedDate: "Oct 24, 2023",
    propertiesCount: 2,
    activity: "2 Properties Listed",
  },
  {
    id: "2",
    name: "Sita Sharma",
    email: "sita.sharma@gmail.com",
    role: "tenant",
    avatar: "/nepali-woman-professional.png",
    status: "active",
    joinedDate: "Oct 20, 2023",
    activity: "Active Search",
  },
  {
    id: "3",
    name: "Anil Karki",
    email: "anil.k@hotmail.com",
    role: "landlord",
    avatar: "",
    status: "pending",
    joinedDate: "Oct 18, 2023",
    propertiesCount: 1,
    activity: "1 Property (Draft)",
  },
  {
    id: "4",
    name: "Binod Chaudhary",
    email: "binod.c@business.np",
    role: "landlord",
    avatar: "/nepali-man-older-business.jpg",
    status: "suspended",
    joinedDate: "Sep 12, 2023",
  },
  {
    id: "5",
    name: "Pooja Thapa",
    email: "pooja.t@gmail.com",
    role: "tenant",
    avatar: "",
    status: "active",
    joinedDate: "Aug 05, 2023",
    activity: "Saved 12 items",
  },
]

// Mock Tenant Requests
export const mockTenantRequests: TenantRequest[] = [
  {
    id: "1",
    tenant: {
      name: "Ramesh Poudel",
      occupation: "Software Engineer",
      avatar: "/nepali-man-young-professional.jpg",
    },
    property: {
      name: "2 BHK Apartment",
      location: "Lalitpur, Nepal",
    },
    moveInDate: "Nov 01, 2023",
    trustScore: 98,
  },
  {
    id: "2",
    tenant: {
      name: "Sita Sharma",
      occupation: "Bank Manager",
      avatar: "/nepali-woman-bank-manager.jpg",
    },
    property: {
      name: "Modern Studio",
      location: "Kathmandu, Nepal",
    },
    moveInDate: "Oct 25, 2023",
    trustScore: 92,
  },
  {
    id: "3",
    tenant: {
      name: "Arjun Karki",
      occupation: "Freelancer",
      avatar: "/nepali-man-freelancer-creative.jpg",
    },
    property: {
      name: "2 BHK Apartment",
      location: "Lalitpur, Nepal",
    },
    moveInDate: "Nov 15, 2023",
    trustScore: 75,
  },
]

// Mock Visits
export const mockVisits: Visit[] = [
  {
    id: "1",
    time: "4:00 PM",
    type: "in-person",
    visitor: "Sita Sharma",
    property: "Modern Studio, Kathmandu",
    status: "confirmed",
  },
  {
    id: "2",
    time: "6:30 PM",
    type: "virtual",
    visitor: "John Doe",
    location: "Zoom Meeting",
    property: "2 BHK Apartment",
    status: "pending",
  },
  {
    id: "3",
    time: "10:00 AM",
    type: "in-person",
    visitor: "Hari Krishna",
    property: "2 BHK Apartment",
    status: "confirmed",
  },
]

// Mock Applications
export const mockApplications: Application[] = [
  {
    id: "1",
    property: {
      title: "2BHK Apartment in Lalitpur",
      location: "Sanepa, Lalitpur",
      price: 35000,
      image: "/apartment-balcony-plants-green.jpg",
    },
    appliedDate: "Oct 24, 2023",
    status: "reviewing",
  },
  {
    id: "2",
    property: {
      title: "Cozy Studio near Thamel",
      location: "Paknajol, Kathmandu",
      price: 18000,
      image: "/cozy-studio-window-light.jpg",
    },
    appliedDate: "Oct 20, 2023",
    status: "approved",
    actionRequired: "Sign Lease",
  },
]

// Mock Lease Info
export const mockLeases: LeaseInfo[] = [
  {
    id: "lease-1",
    propertyId: "1",
    propertyTitle: "Sunny 2BHK in Lalitpur",
    propertyAddress: "Jhamsikhel, Lalitpur, Nepal",
    tenantName: "Aarav Sharma",
    tenantEmail: "aarav.sharma@email.com",
    startDate: "2023-11-01",
    endDate: "2024-10-31",
    monthlyRent: 25000,
    status: "active",
  },
  {
    id: "lease-2",
    propertyId: "2",
    propertyTitle: "Cozy Studio near Thamel",
    propertyAddress: "Paknajol, Kathmandu, Nepal",
    tenantName: "Priya Thapa",
    tenantEmail: "priya.thapa@email.com",
    startDate: "2023-06-01",
    endDate: "2024-05-31",
    monthlyRent: 18000,
    status: "active",
  },
]

// Mock Rent Payments
export const mockRentPayments: RentPayment[] = [
  { id: "1", month: "October 2023", amount: 25000, status: "paid", paidDate: "Oct 05, 2023" },
  { id: "2", month: "November 2023", amount: 25000, status: "paid", paidDate: "Nov 03, 2023" },
  { id: "3", month: "December 2023", amount: 25000, status: "pending" },
  { id: "4", month: "January 2024", amount: 25000, status: "pending" },
]

// Mock current user for role-based views
export type UserRole = "tenant" | "landlord" | "admin"

export const mockCurrentUser = {
  id: "current-user",
  name: "Aarav Sharma",
  email: "aarav@example.com",
  role: "tenant" as UserRole,
  avatar: "/nepali-young-man-smiling.jpg",
}
