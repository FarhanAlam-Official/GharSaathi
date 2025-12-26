import type React from "react"
import { cn } from "@/lib/utils"
import { Card, CardContent } from "@/components/ui/card"
import { TrendingUp, TrendingDown } from "lucide-react"

interface StatsCardProps {
  title: string
  value: string | number
  change?: {
    value: string
    trend: "up" | "down" | "neutral"
  }
  subtitle?: string
  icon?: React.ReactNode
  variant?: "default" | "dark"
  className?: string
}

export function StatsCard({ title, value, change, subtitle, icon, variant = "default", className }: StatsCardProps) {
  const isDark = variant === "dark"

  return (
    <Card
      className={cn(
        "overflow-hidden",
        isDark ? "bg-[#1e293b] border-[#334155] text-slate-100" : "bg-card border-border",
        className,
      )}
    >
      <CardContent className="p-6">
        <div className="flex items-start justify-between">
          <div>
            <p className={cn("text-sm font-medium", isDark ? "text-slate-400" : "text-muted-foreground")}>{title}</p>
            <p className="mt-2 text-3xl font-bold">{value}</p>
            {change && (
              <div className="mt-2 flex items-center gap-1">
                {change.trend === "up" ? (
                  <TrendingUp className="h-4 w-4 text-emerald-500" />
                ) : change.trend === "down" ? (
                  <TrendingDown className="h-4 w-4 text-red-500" />
                ) : null}
                <span
                  className={cn(
                    "text-sm font-medium",
                    change.trend === "up"
                      ? "text-emerald-500"
                      : change.trend === "down"
                        ? "text-red-500"
                        : "text-muted-foreground",
                  )}
                >
                  {change.value}
                </span>
              </div>
            )}
            {subtitle && (
              <p className={cn("mt-1 text-sm", isDark ? "text-slate-400" : "text-muted-foreground")}>{subtitle}</p>
            )}
          </div>
          {icon && (
            <div
              className={cn(
                "flex h-12 w-12 items-center justify-center rounded-xl",
                isDark ? "bg-slate-700" : "bg-muted",
              )}
            >
              {icon}
            </div>
          )}
        </div>
      </CardContent>
    </Card>
  )
}
