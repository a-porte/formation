data "aws_ami" "ami-ubuntu" {
  most_recent = true
  owners = [137112412989] # got thanks to command line aws ec2 describe-images --region eu-west-3 --image-ids ami-0f82b13d37cd1e8cc

  filter {
    name   = "name"
    values = ["al2023-ami-2023.2.20231002.0-kernel-6.1-x86_64"]
    #this filter is similar to the following command line
    #  aws ec2 describe-images --owners 137112412989 --region eu-west-3 --filters 'Name=name,Values=al2023-ami-2023.2.20231002.0-kernel-6.1-x86_64'
  }
}

# requires `terraform init -upgrade`
data "external" "name" {
  program = ["python", "scripts/rand-name.py"]
}
