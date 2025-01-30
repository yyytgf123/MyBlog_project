output "mb_alb_dn" {
  value = aws_lb.mb_alb.dns_name
}

output "mb_alb_tg" {
  value = aws_lb_target_group.mb_alb_target_group.arn
}

output "mb_alb_zond_id" {
  value = aws_lb.mb_alb.zone_id
}