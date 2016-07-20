/**
  * You will definitely need to do some work in this file.
  */

trait RsaBreaker {
	def recoverKey(publicKey: RsaPublicKey): RsaKey
	def bruteForceDecrypt(m: String, publicKey: RsaPublicKey): String
}

object RsaBreaker extends RsaBreaker {
	// This presumes n = p * q
	override def recoverKey(publicKey: RsaPublicKey): RsaKey = {
		// throw new NotImplementedError("You must implement recoverKey(n) yourself")
		val factored = factor(publicKey.n)

		val p = factored.head
		val q = factored.tail.head

		val phiN = MathHelper.eulerTotient(p, q)

		val d = MathHelper.modularInverse(RsaCryptosystem.e, phiN).get

		RsaKey(publicKey, d, (p, q), phiN)

		/**
		  * Alternate solution (cheating, less fun, does not demonstrate purpose of e):
		  *
		  * RsaCryptosystem.generateKey(p, q)
		  */
	}

	// Call this with the RsaPublicKey() that you create and the message m you have
	override def bruteForceDecrypt(m: String, publicKey: RsaPublicKey): String = RsaCryptosystem.decrypt(m, recoverKey(publicKey))

	private def factor(n: Int): Seq[Int] = {
		// throw new NotImplementedError("You must implement factor(n) yourself")

		for (i <- 2 until n if n % i == 0) yield i

		/**
		  * For learning "yield" point them to RsaCryptosystem.scala's encrypt/decrypt implementations
		  *
		  * Alternate solution:
		  *
		  * for (i <- 2 to n - 1 if n % i == 0) yield i
		  */
	}
}
