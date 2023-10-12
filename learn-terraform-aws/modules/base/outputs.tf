output "aws_instance_ip" {
  value = aws_instance.my_ec2_instance.public_ip
}


output "aws_secu_grp_id" {
  value = aws_security_group.secu_grp.id
}