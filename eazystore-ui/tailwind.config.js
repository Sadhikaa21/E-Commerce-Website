/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}"
  ],
  theme: {
    extend: {
      colors: {
        primary: "var(--color-primary)",
        dark: "var(--color-dark)",
        light: "var(--color-light)",
        lighter: "var(--color-lighter)",
      },
      fontFamily: {
        primary: ['"Josefin Sans"', "sans-serif"]
      }
    }
  },
  darkMode: "class",
  plugins: [],
}
