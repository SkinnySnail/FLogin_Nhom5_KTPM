import { render, screen } from '@testing-library/react';
import App from './App';

test('renders App component with Navbar', () => {
  render(<App />);
  // Check if "Full Stack Application" text from Navbar is present
  const navElement = screen.getByText(/Full Stack Application/i);
  expect(navElement).toBeInTheDocument();
});
