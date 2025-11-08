package ar.com.ericpennachini.fashiondog.app.domain.extensions

import ar.com.ericpennachini.fashiondog.app.domain.model.Customer

/**
 * Returns a representation of a [Customer] in terms of its `firstName` and `lastName`
 */
fun Customer.fullName() = "$firstName $lastName"