output "public_ip" {
  value = module.base.aws_instance_ip # aws_instance.my_ec2_instance.public_ip
}

output "endpoint" {
  value = module.website_s3_bucket.website_endpoint
}

output "bucket_name" {
  value = module.website_s3_bucket.name
}

output "dynamic_tag" {
  value = module.dynamic_block.autoscale_grp_tags
}
output "custom_for" {
  value = module.dynamic_block.custom_list
}

output "plus2" {
  value = module.operations.plus_2
}

output "is_even" {
  value = module.operations.is_even
}

output "env" {
  value = module.operations.wrapped_env_name
}
