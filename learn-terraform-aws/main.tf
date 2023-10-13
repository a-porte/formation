provider "aws" {
  region = var.AWS_REGION  # or "${var.AWS_REGION}"
  access_key = var.SECRET.access_key
  secret_key = var.SECRET["secret_key"] #both way to access a map element
}

module "website_s3_bucket" {
  source = "./modules/aws-s3"
}

module "data" {
  source = "./modules/data"
}

module "base" {
  source = "./modules/base"
}

module "dynamic_block" {
  source = "./modules/dynamic"
}

# One can download a module from a remote repo like this
# module "name" {
#  source = "<repo @>"
# }

# module "name" {
#  source = "hashicop/..."
#  version = ...
# }




output "public_ip" {
  value = module.base.aws_instance_ip # aws_instance.my_ec2_instance.public_ip
}

output "endpoint" {
  value = module.website_s3_bucket.website_endpoint
}

output "bucket_name" {
  value = module.website_s3_bucket.name
}


#if this block is added whereas a local backend already exists
# execute `terraform init -migrate-state` : backend info will be sent to the S3 bucket
terraform {
  backend "s3" {
    bucket = "my-ec3-bucket-11102023"
    key = "states/terraform.states"
    region = "eu-west-3"
  }
}