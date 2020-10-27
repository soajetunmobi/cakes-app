import { Cake } from '../types/types'
import { useCallback, useEffect, useState } from 'react'
import { cakeService } from '../service'

interface IFetchCakes {
  cakes: Cake[] | undefined
  isLoading: boolean
  error: string | undefined
}
export const useFetchCakes = (): IFetchCakes => {
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [error, setError] = useState<string>()
  const [allCakes, setAllCakes] = useState<Cake[]>()

  const fetchCakesHandler = useCallback(async () => {
    setError(undefined)
    setIsLoading(true)
    try {
      const response = await cakeService.getAll()
      setAllCakes(response)
    } catch (err) {
      setError(err)
    }
    setIsLoading(false)
  }, [setAllCakes, setIsLoading, setError])

  useEffect(() => {
    fetchCakesHandler()
  }, [fetchCakesHandler])

  return { cakes: allCakes, isLoading, error }
}
