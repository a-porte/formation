output "website_endpoint" {
  value = aws_s3_bucket_website_configuration.my_website_conf.website_endpoint
}


output "name" {
  value = aws_s3_bucket.my_bucket.id
}