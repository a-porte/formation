output "plus_2" {
  value = {for x in var.MY_INTS : x => x+2 }
}

output "is_even" {
  value = {for x in var.MY_INTS : x => 0 == x%2}
}

output "wrapped_env_name" {
  value = [for is_prod in [true, false]: "environment name : ${is_prod ? var.ENV_NAME[0] :var.ENV_NAME[1]  }"]
  # a ternary operator is used for the sake of demonstration
  # a map could have done the trick
}