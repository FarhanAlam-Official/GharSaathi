"use client"

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { FileText, Clock, CheckCircle2, XCircle, AlertCircle, ArrowRight, Eye, X } from "lucide-react"
import { mockApplications } from "@/lib/mock-data"
import Image from "next/image"
import Link from "next/link"

export default function TenantApplicationsPage() {
  return (
    <div className="flex-1 p-8">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-bold text-foreground">My Applications</h1>
        <p className="mt-1 text-muted-foreground">Track your rental applications and their current status.</p>
      </div>

      {/* Stats */}
      <div className="mt-8 grid gap-6 md:grid-cols-4">
        <Card className="bg-card">
          <CardContent className="p-6 flex items-center gap-4">
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-blue-100 text-blue-600">
              <FileText className="h-6 w-6" />
            </div>
            <div>
              <p className="text-sm text-muted-foreground">Total Applications</p>
              <p className="text-2xl font-bold">5</p>
            </div>
          </CardContent>
        </Card>

        <Card className="bg-card">
          <CardContent className="p-6 flex items-center gap-4">
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-amber-100 text-amber-600">
              <Clock className="h-6 w-6" />
            </div>
            <div>
              <p className="text-sm text-muted-foreground">Pending Review</p>
              <p className="text-2xl font-bold">2</p>
            </div>
          </CardContent>
        </Card>

        <Card className="bg-card">
          <CardContent className="p-6 flex items-center gap-4">
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-emerald-100 text-emerald-600">
              <CheckCircle2 className="h-6 w-6" />
            </div>
            <div>
              <p className="text-sm text-muted-foreground">Approved</p>
              <p className="text-2xl font-bold">2</p>
            </div>
          </CardContent>
        </Card>

        <Card className="bg-card">
          <CardContent className="p-6 flex items-center gap-4">
            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-red-100 text-red-600">
              <XCircle className="h-6 w-6" />
            </div>
            <div>
              <p className="text-sm text-muted-foreground">Rejected</p>
              <p className="text-2xl font-bold">1</p>
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Active Applications */}
      <Card className="mt-8">
        <CardHeader>
          <CardTitle>Active Applications</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          {mockApplications.map((app) => (
            <div key={app.id} className="flex items-center gap-4 rounded-lg border border-border p-4">
              <div className="relative h-24 w-36 rounded-lg overflow-hidden shrink-0">
                <Image
                  src={app.property.image || "/placeholder.svg"}
                  alt={app.property.title}
                  fill
                  className="object-cover"
                />
              </div>
              <div className="flex-1 min-w-0">
                <h3 className="font-semibold text-foreground">{app.property.title}</h3>
                <p className="mt-1 text-sm text-muted-foreground">
                  {app.property.location} • Rs. {app.property.price.toLocaleString()}/mo
                </p>
                <p className="mt-2 text-xs text-muted-foreground">Applied: {app.appliedDate}</p>
                {app.actionRequired && (
                  <div className="mt-2 flex items-center gap-2 text-sm text-amber-600">
                    <AlertCircle className="h-4 w-4" />
                    Action Required: {app.actionRequired}
                  </div>
                )}
              </div>
              <div className="flex flex-col items-end gap-3">
                <Badge
                  className={
                    app.status === "reviewing"
                      ? "bg-amber-100 text-amber-700 border-amber-200"
                      : app.status === "approved"
                        ? "bg-emerald-100 text-emerald-700 border-emerald-200"
                        : "bg-red-100 text-red-700 border-red-200"
                  }
                  variant="outline"
                >
                  {app.status === "reviewing" ? (
                    <>
                      <Clock className="mr-1 h-3 w-3" />
                      Under Review
                    </>
                  ) : app.status === "approved" ? (
                    <>
                      <CheckCircle2 className="mr-1 h-3 w-3" />
                      Approved
                    </>
                  ) : (
                    <>
                      <XCircle className="mr-1 h-3 w-3" />
                      Rejected
                    </>
                  )}
                </Badge>
                <div className="flex gap-2">
                  {app.status === "approved" ? (
                    <>
                      <Button variant="outline" size="sm" className="gap-1 bg-transparent">
                        <X className="h-4 w-4" />
                        Decline
                      </Button>
                      <Button size="sm" className="gap-1">
                        <Eye className="h-4 w-4" />
                        Review Lease
                      </Button>
                    </>
                  ) : (
                    <>
                      <Button variant="outline" size="sm" className="bg-transparent">
                        Withdraw
                      </Button>
                      <Button size="sm" asChild>
                        <Link href={`/properties/${app.id}`}>
                          View Details <ArrowRight className="ml-1 h-4 w-4" />
                        </Link>
                      </Button>
                    </>
                  )}
                </div>
              </div>
            </div>
          ))}
        </CardContent>
      </Card>

      {/* Past Applications */}
      <Card className="mt-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle>Application History</CardTitle>
          <Button variant="link" className="text-primary h-auto p-0 gap-1">
            View All <ArrowRight className="h-4 w-4" />
          </Button>
        </CardHeader>
        <CardContent>
          <div className="space-y-3">
            {[
              { title: "Modern Flat in Thamel", status: "Expired", date: "Sep 15, 2023" },
              { title: "Cozy Room in Patan", status: "Withdrawn", date: "Aug 28, 2023" },
            ].map((item, index) => (
              <div key={index} className="flex items-center justify-between rounded-lg border border-border p-4">
                <div>
                  <p className="font-medium">{item.title}</p>
                  <p className="text-sm text-muted-foreground">{item.date}</p>
                </div>
                <Badge variant="secondary">{item.status}</Badge>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  )
}
