# DeSHA256

If you have the encrypted code(SHA-256 hash), looks like "0a1533ef43e1a6fbe3566f1d7dcd9cd76f57e0d53c7ed86a49b94a81a76c99e8", you can use this program to decrypt it and get the original infomation.

# How does it work?
This program goes through all the possibile combinations of visible ASCII codes, a-z, A-Z, 0-9, comma, period, brackets etc. 94 in total, hashes each combo using SHA-256, compares the hash result with the original hashed/encrypted code, until a match is found.

# How fast?
* approx. 2.5 million hashes per second on a gaming laptop(Intel Core i7-4700HQ 2.4GHz, Win 8.1 64-bit).
* approx. 300 milliseconds to find a 3-character long password.
* approx. 21 seconds to find a 4-character long password.

# DeSHA256 on GPU/Cloud
DeSHA256 can be setup to run on GPU and/or clouds to achieve a even better performance.
