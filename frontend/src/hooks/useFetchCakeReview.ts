import { IHistory, Review } from '../types/types'
import { useCallback, useEffect, useState } from 'react'
import { cakeService } from '../service'
import { useHistory } from 'react-router-dom'
import { routes } from '../common/const/routes'

interface IFetchCakes {
  review: Review | undefined
  isLoading: boolean
  error: string | undefined
}
export const useFetchCakeReview = (): IFetchCakes => {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState()
  const [review, setReview] = useState<Review>()

  const history = useHistory<IHistory>()

  const fetchReviewHandler = useCallback(async () => {
    setError(undefined)
    setIsLoading(true)
    try {
      if (!history.location.state || !history.location.state.id) {
        history.push(routes.cakes)
      }
      const { id } = history.location.state
      const response = await cakeService.getById(id)
      setReview(response)
    } catch (err) {
      setError(err)
    }
    setIsLoading(false)
  }, [setIsLoading, setError])

  useEffect(() => {
    fetchReviewHandler()
  }, [fetchReviewHandler])

  return { review, isLoading, error }
}
