"use client"

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Bell, Settings, Filter, Calendar, Users, Mail, MapPin, Plus, Video } from "lucide-react"
import { mockTenantRequests } from "@/lib/mock-data"

export default function LandlordRequestsPage() {
  return (
    <div className="flex-1 bg-[#0f172a] text-slate-100 p-8">
      {/* Breadcrumb & Actions */}
      <div className="flex items-center justify-between">
        <div>
          <div className="flex items-center gap-2 text-sm text-slate-400">
            <span>Dashboard</span>
            <span>/</span>
            <span className="text-primary">Requests & Visits</span>
          </div>
          <h1 className="mt-4 text-3xl font-bold">Requests & Visits</h1>
          <p className="mt-1 text-slate-400">
            Manage your prospective tenants efficiently, review applications, and keep track of scheduled property
            tours.
          </p>
        </div>
        <div className="flex items-center gap-3">
          <Button variant="secondary" className="gap-2 bg-slate-700 hover:bg-slate-600">
            <Filter className="h-4 w-4" />
            Filter
          </Button>
          <Button className="gap-2 bg-primary hover:bg-primary/90">
            <Calendar className="h-4 w-4" />
            View Calendar
          </Button>
        </div>
      </div>

      {/* User Profile */}
      <div className="absolute top-8 right-8 flex items-center gap-3">
        <Button variant="ghost" size="icon" className="text-slate-400 relative">
          <Bell className="h-5 w-5" />
          <span className="absolute -top-1 -right-1 h-4 w-4 rounded-full bg-destructive text-[10px] font-medium text-white flex items-center justify-center">
            2
          </span>
        </Button>
        <Button variant="ghost" size="icon" className="text-slate-400">
          <Settings className="h-5 w-5" />
        </Button>
        <div className="flex items-center gap-2 ml-2">
          <div className="text-right">
            <p className="text-sm font-medium">Rajesh Hamal</p>
            <p className="text-xs text-slate-400">Super Landlord</p>
          </div>
          <Avatar className="h-10 w-10">
            <AvatarImage src="/placeholder.svg?height=40&width=40" />
            <AvatarFallback>RH</AvatarFallback>
          </Avatar>
        </div>
      </div>

      {/* Stats */}
      <div className="mt-8 grid gap-6 md:grid-cols-3">
        <Card className="bg-slate-800/50 border-slate-700">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-slate-400">Pending Requests</p>
              <p className="mt-2 text-4xl font-bold">5</p>
              <p className="mt-1 text-sm text-emerald-400">+1 today</p>
            </div>
            <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-primary/20">
              <Users className="h-7 w-7 text-primary" />
            </div>
          </CardContent>
        </Card>

        <Card className="bg-slate-800/50 border-slate-700">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-slate-400">Upcoming Visits</p>
              <p className="mt-2 text-4xl font-bold">2</p>
              <p className="mt-1 text-sm text-slate-400">Next in 2h</p>
            </div>
            <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-blue-500/20">
              <Calendar className="h-7 w-7 text-blue-400" />
            </div>
          </CardContent>
        </Card>

        <Card className="bg-slate-800/50 border-slate-700">
          <CardContent className="p-6 flex items-center justify-between">
            <div>
              <p className="text-sm text-slate-400">Total Inquiries</p>
              <p className="mt-2 text-4xl font-bold">12</p>
              <p className="mt-1 text-sm text-emerald-400">+3 this week</p>
            </div>
            <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-emerald-500/20">
              <Mail className="h-7 w-7 text-emerald-400" />
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Main Content */}
      <div className="mt-8 grid gap-6 lg:grid-cols-2">
        {/* Recent Tenant Requests */}
        <Card className="bg-slate-800/50 border-slate-700">
          <CardHeader className="flex flex-row items-center justify-between">
            <CardTitle>Recent Tenant Requests</CardTitle>
            <Button variant="link" className="text-primary h-auto p-0">
              View All
            </Button>
          </CardHeader>
          <CardContent>
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead>
                  <tr className="text-left text-xs text-slate-400 uppercase">
                    <th className="pb-4 font-medium">Tenant</th>
                    <th className="pb-4 font-medium">Property</th>
                    <th className="pb-4 font-medium">Move-in</th>
                    <th className="pb-4 font-medium">Trust Score</th>
                  </tr>
                </thead>
                <tbody className="text-sm">
                  {mockTenantRequests.map((request) => (
                    <tr key={request.id} className="border-t border-slate-700">
                      <td className="py-4">
                        <div className="flex items-center gap-3">
                          <Avatar className="h-10 w-10">
                            <AvatarImage src={request.tenant.avatar || "/placeholder.svg"} />
                            <AvatarFallback>{request.tenant.name.charAt(0)}</AvatarFallback>
                          </Avatar>
                          <div>
                            <p className="font-medium">{request.tenant.name}</p>
                            <p className="text-xs text-slate-400">{request.tenant.occupation}</p>
                          </div>
                        </div>
                      </td>
                      <td className="py-4">
                        <p className="font-medium">{request.property.name}</p>
                        <p className="text-xs text-slate-400">{request.property.location}</p>
                      </td>
                      <td className="py-4 text-slate-300">{request.moveInDate}</td>
                      <td className="py-4">
                        <Badge
                          className={`${
                            request.trustScore >= 90
                              ? "bg-emerald-500/20 text-emerald-400"
                              : request.trustScore >= 75
                                ? "bg-amber-500/20 text-amber-400"
                                : "bg-red-500/20 text-red-400"
                          }`}
                        >
                          {request.trustScore >= 90 ? "✓" : "★"} {request.trustScore}%
                        </Badge>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
            <div className="mt-4 flex items-center justify-between text-sm text-slate-400">
              <span>Showing 3 of 5 requests</span>
              <div className="flex gap-2">
                <Button variant="outline" size="sm" className="h-8 border-slate-600 bg-transparent">
                  Prev
                </Button>
                <Button variant="outline" size="sm" className="h-8 border-slate-600 bg-slate-700">
                  Next
                </Button>
              </div>
            </div>
          </CardContent>
        </Card>

        {/* Upcoming Visits */}
        <Card className="bg-slate-800/50 border-slate-700">
          <CardHeader className="flex flex-row items-center justify-between">
            <CardTitle>Upcoming Visits</CardTitle>
            <Button variant="ghost" size="icon" className="text-slate-400">
              <Settings className="h-4 w-4" />
            </Button>
          </CardHeader>
          <CardContent>
            <div className="space-y-6">
              {/* Today */}
              <div>
                <Badge className="bg-primary/20 text-primary">Today, Oct 14</Badge>
                <div className="mt-4 space-y-4">
                  <div className="flex items-start gap-3 border-l-2 border-primary pl-4">
                    <div className="flex-1">
                      <div className="flex items-center justify-between">
                        <p className="font-semibold">4:00 PM</p>
                        <Badge className="bg-emerald-500/20 text-emerald-400">Confirmed</Badge>
                      </div>
                      <p className="mt-1">Tour with Sita Sharma</p>
                      <p className="mt-1 flex items-center gap-1 text-sm text-slate-400">
                        <MapPin className="h-3 w-3" />
                        Modern Studio, Kathmandu
                      </p>
                    </div>
                  </div>
                  <div className="flex items-start gap-3 border-l-2 border-slate-600 pl-4">
                    <div className="flex-1">
                      <div className="flex items-center justify-between">
                        <p className="font-semibold">6:30 PM</p>
                        <Badge variant="outline" className="border-slate-600 text-slate-400">
                          Pending
                        </Badge>
                      </div>
                      <p className="mt-1">Virtual Tour with John Doe</p>
                      <p className="mt-1 flex items-center gap-1 text-sm text-slate-400">
                        <Video className="h-3 w-3" />
                        Zoom Meeting
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              {/* Tomorrow */}
              <div>
                <Badge variant="secondary" className="bg-slate-700 text-slate-300">
                  Tomorrow, Oct 15
                </Badge>
                <div className="mt-4 space-y-4">
                  <div className="flex items-start gap-3 border-l-2 border-slate-600 pl-4">
                    <div className="flex-1">
                      <p className="font-semibold">10:00 AM</p>
                      <p className="mt-1">Tour with Hari Krishna</p>
                      <p className="mt-1 flex items-center gap-1 text-sm text-slate-400">
                        <MapPin className="h-3 w-3" />2 BHK Apartment
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              <Button
                variant="outline"
                className="w-full gap-2 border-dashed border-slate-600 text-slate-400 hover:bg-slate-700 bg-transparent"
              >
                <Plus className="h-4 w-4" />
                Add Manual Visit
              </Button>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
