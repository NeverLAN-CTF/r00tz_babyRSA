object RsaEx extends App {
	/**
	  * RSA is a common public key encryption algorithm that is based on the idea that we can
	  * multiply two really big prime numbers together easily, but if you give someone
	  * the product of two large primes, it's hard to factor it apart again.
	  *
	  * We need to learn about modular arithmetic first. This is the arithmetic of a clock:
	  * 23:00 (11 PM) + 7 hours = 30:00 = 06:00 (6 AM). Another way to say this is that
	  * 30 is congruent to 6 (mod 24), or that 30 mod 24 equals 6, or in Scala:
	  * 6 == 30 % 24
	  *
	  * We'll be seeing a lot of numbers "mod" (%) another number.
	  *
	  * Here is how RSA works:
	  *
	  * Alice wants to send a message to Bob.
	  *
	  * So Bob picks two huge random prime numbers, p and q. He keeps these a secret.
	  *
	  * Bob multiplies them together to get n = p * q. n will be public.
	  *
	  * Bob computes phi(n) = (p - 1) * (q - 1) and keeps this as a secret. Phi(n) is called Euler's
	  * totient function (read about it later!).
	  *
	  * Bob picks a value e between 1 and phi(n) such that phi(n) and e have no common factors; here Bob
	  * chooses 17. e will be public.
	  *
	  * Bob calculates the secret key (which must be kept secret) d = e inverse mod phi(n) -- the secret
	  * value that will undo his encryption, because e * d = 1 mod phi(n).
	  *
	  * Finally, Bob gives Alice (n, e) as the public key and keeps (d, n, e) as the private key for himself.
	  *
	  * Alice now takes her message X and encrypts the message as C = Math.pow(X, e) % n. She sends this to Bob.
	  *
	  * Bob decrypts the message as M back to
	  * M = Math.pow(C, d) % n
	  * = Math.pow(Math.pow(X, e), d) % n
	  * = Math.pow(X, e * d) % n
	  * = X % n
	  *
	  * In a real cryptosystem, p and q are chosen to be so big that it's hard to factor n. In a toy cryptosystem
	  * like this one, we can use small primes to make things easier (possible).
	  *
	  */

	/**
	  * Let's demonstrate RSA using a random key to get the hang of things.
	  * This MIGHT fail. If it does, just run it again.
	  * This code deliberately uses very small numbers. That's what makes it possible for us to break.
	  */
	val randomKey = RsaCryptosystem.generateRandomKey
	Console.println(s"We randomly generated this key:\n$randomKey")

	val cleartext = "hello, hackers"
	Console.println(s"Cleartext: $cleartext")

	val ciphertext = RsaCryptosystem.encrypt(cleartext, randomKey)
	Console.println(s"Ciphertext: $ciphertext")

	val decryptedCleartext = RsaCryptosystem.decrypt(ciphertext, randomKey)
	Console.println(s"Decrypted back: $decryptedCleartext")

	/**
	  * You have stolen the following ciphertext:
	  * 63 12471 17384 19150 3861 17806 15090 5270
	  *
	  * This was encrypted using an RSA key with the following public key:
	  * n = 19153
	  * e = 17
	  *
	  * Break the key and decrypt the ciphertext to get the flag
	  */

	/**
	  * todo: removeme:
	  * Suggest removing these three lines or gutting severely;
	  * another valid solution might not include RsaBreaker at all but just procedural code of same purpose
	  *
	  * Flag decrypts to: harmonic
	  */
	val encryptedFlag = "63 12471 17384 19150 3861 17806 15090 5270"
	val decryptedFlag = RsaBreaker.bruteForceDecrypt(encryptedFlag, RsaPublicKey(n = 19153, e = 17))
	Console.println(s"Decrypted to: $decryptedFlag")
}
