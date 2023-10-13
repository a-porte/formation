module "data" {
  source = "./../data"
}


resource "aws_autoscaling_group" "my_autoscaling_gp" {
  availability_zones = ["eu-west-3a"]
  #zone knows thanks to aws ec2 describe-availability-zones --region eu-west-3` command line
  max_size = 5
  min_size = 0
# declaration of multiple tags => code duplication
  tag {
    key                 = "env"
    propagate_at_launch = false
    value               = "prod"
  }

  tag {
    key                 = "domain"
    propagate_at_launch = false
    value               = "risk"
  }

  mixed_instances_policy {
    launch_template {
      launch_template_specification {
        launch_template_id = aws_launch_template.launch_template.id
      }
    }
  }
}


resource "aws_launch_template" "launch_template" {
  name_prefix = "name-prefix"
  image_id = module.data.ami_id
  instance_type = "c5.large"
}