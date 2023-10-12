output "ami_id" {
  value =  data.aws_ami.ami-ubuntu.id
}
output "random_name" {
  value = data.external.name.result.random_name
}
