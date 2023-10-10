provider "aws" {
  region = "eu-west-3"
  access_key = ""
  secret_key = ""
}

resource "aws_instance" "my_ec2_instance" {
  ami = "ami-0f82b13d37cd1e8cc"
  instance_type = "t2.nano"
}