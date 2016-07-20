import scala.collection.mutable.ListBuffer

trait RsaBreaker {
	def recoverKey(publicKey: RsaPublicKey): RsaKey
	def bruteForceDecrypt(m: String, publicKey: RsaPublicKey): String
}

object RsaBreaker extends RsaBreaker {
	// todo: removeme: Suggest (mostly?) gutting, otherwise the challenge becomes much smaller (math understanding portion removed)
	// This presumes n = p * q
	override def recoverKey(publicKey: RsaPublicKey): RsaKey = {
		val factored = factor(publicKey.n)

		val p = factored.head._1
		val q = factored.tail.head._1

		val phiN = MathHelper.eulerTotient(p, q)

		val d = MathHelper.modularInverse(RsaCryptosystem.e, phiN).get

		RsaKey(publicKey, d, (p, q), phiN)

		/**
		  * todo: removeme
		  *
		  * Alternate solution (cheating, less fun, does not demonstrate purpose of e):
		  *
		  * RsaCryptosystem.generateKey(p, q)
		  */
	}

	// todo: removeme: Suggest leaving this and having kids use it; it's just a convenience method anyway
	override def bruteForceDecrypt(m: String, publicKey: RsaPublicKey): String = RsaCryptosystem.decrypt(m, recoverKey(publicKey))

	// todo: removeme: Suggest entirely gutting. This is the crux of the RSA problem.
	private def factor(n: Int): Seq[(Int, Int)] = {
		val factors = for (i <- 2 until n if n % i == 0) yield i

		val res: ListBuffer[(Int, Int)] = ListBuffer()

		var x = n
		for (i <- factors) {
			var c = 0

			while (x % i == 0) {
				c += 1
				x /= i
			}

			if (c > 0) res += ((i, c))
		}

		res
	}
}
