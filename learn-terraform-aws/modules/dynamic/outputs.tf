output "autoscale_grp_tags" {
  value = [for k, v in var.MY_TAGS : "<key is ${k} and v is ${v}>"]
}

output "custom_list" {
  value = [for key in [1, 2, 3] : "<key is ${key}> "]
}
