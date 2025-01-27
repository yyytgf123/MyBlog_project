output "bastion_ec2_ip" {
  value = aws_eip.mb_ec2_eip.id
}

# output "private_ec2_ip" {
#   value = aws_eip.mb_private_ec2 // 수정 필요
# }

# output "mb_ec2_launch_template" {
#   value = aws_launch_template.mb_ec2_launch_template.id
# }