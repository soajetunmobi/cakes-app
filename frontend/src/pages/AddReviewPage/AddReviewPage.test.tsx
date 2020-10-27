import React from 'react'
import renderer from 'react-test-renderer'
import { AddReviewPage } from './AddReviewPage'
import { render, fireEvent } from '@testing-library/react'

describe('Add review component', () => {
  test('it renders correctly', () => {
    const root = renderer.create(<AddReviewPage />).toJSON()
    expect(root).toMatchSnapshot()
  })

  describe('Add review form is rendered', () => {
    it('should render form', async () => {
      const { getByText } = render(<AddReviewPage />)
      const nameInput = getByText('Name')
      expect(nameInput).toBeInTheDocument()
      const imageInput = getByText('Image url')
      expect(imageInput).toBeInTheDocument()
      const commentInput = getByText('Comment')
      expect(commentInput).toBeInTheDocument()
      const yumFactorContainer = getByText('Yum factor')
      expect(yumFactorContainer).toBeInTheDocument()
    })

    it('should show error messages in form', async () => {
      const { getByTestId } = render(<AddReviewPage />)
      const nameError = getByTestId('name-error')
      expect(nameError).toBeInTheDocument()
      const imageError = getByTestId('image-error')
      expect(imageError).toBeInTheDocument()
      const commentError = getByTestId('comment-error')
      expect(commentError).toBeInTheDocument()
      const yumError = getByTestId('error-yumfactor')
      expect(yumError).toBeInTheDocument()
    })

    it('should not show error messages in form when data is entered', async () => {
      const { getByTestId, queryByTestId } = render(<AddReviewPage />)
      const nameInput = getByTestId('review-name')
      fireEvent.change(nameInput, { target: { value: 'Cake' } })
      expect(queryByTestId('/name-error/i')).toBeNull()

      const imageInput = getByTestId('review-image')
      fireEvent.change(imageInput, { target: { value: 'www.test.com' } })
      expect(queryByTestId('/image-error/i')).toBeNull()

      const commentInput = getByTestId('review-comment')
      fireEvent.change(commentInput, { target: { value: 'This is comment' } })
      expect(queryByTestId('/comment-error/i')).toBeNull()

      const yum3 = getByTestId('yum-3')
      fireEvent.click(yum3)
      expect(queryByTestId('/error-yumfactor/i')).toBeNull()
    })
  })
})
