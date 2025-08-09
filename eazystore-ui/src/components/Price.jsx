import React from 'react'

export default function Price({currency,price}) {
  console.log("Price component loaded");
  return (
   <>
   {currency}
   <span>{price}</span>
   </>
  )
}
