'use client'

import { useState } from 'react'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Alert, AlertDescription } from '@/components/ui/alert'
import apiClient from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import { handleAPIError } from '@/lib/api/errorHandler'
import { CheckCircle2, XCircle, Loader2 } from 'lucide-react'

export function TestAPIConnection() {
  const [status, setStatus] = useState<'idle' | 'loading' | 'success' | 'error'>('idle')
  const [message, setMessage] = useState<string>('')

  const testConnection = async () => {
    setStatus('loading')
    setMessage('')

    try {
      const response = await apiClient.get(API_ENDPOINTS.AUTH.HEALTH)
      setStatus('success')
      setMessage(response.data || 'Backend connection successful!')
    } catch (error) {
      setStatus('error')
      const apiError = handleAPIError(error)
      setMessage(apiError.message)
    }
  }

  return (
    <Card className="w-full max-w-md">
      <CardHeader>
        <CardTitle>Backend Connection Test</CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        <Button 
          onClick={testConnection} 
          disabled={status === 'loading'}
          className="w-full"
        >
          {status === 'loading' && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
          Test Connection
        </Button>

        {status === 'success' && (
          <Alert className="border-green-500 bg-green-50 dark:bg-green-950">
            <CheckCircle2 className="h-4 w-4 text-green-600" />
            <AlertDescription className="text-green-800 dark:text-green-200">
              {message}
            </AlertDescription>
          </Alert>
        )}

        {status === 'error' && (
          <Alert variant="destructive">
            <XCircle className="h-4 w-4" />
            <AlertDescription>
              {message}
            </AlertDescription>
          </Alert>
        )}

        {status === 'idle' && (
          <p className="text-sm text-muted-foreground text-center">
            Click the button to test the connection to the backend server
          </p>
        )}
      </CardContent>
    </Card>
  )
}
